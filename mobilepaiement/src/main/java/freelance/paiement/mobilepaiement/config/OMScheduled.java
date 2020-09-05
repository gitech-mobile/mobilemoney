package freelance.paiement.mobilepaiement.config;

import Dto.Paiement;
import freelance.paiement.mobilepaiement.service.GoipService;
import lombok.Synchronized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static Enum.EtatPaiement.*;

@Component
public class OMScheduled {
    private final Logger            log = LoggerFactory.getLogger(this.getClass());
    private final String            baseUrl = "http://donne";
    private final WebClient.Builder webClientBuilder;
    private final GoipService       goipService;
    private final String            omFixedRate = "${schedule.om.rate}";
    @Value("${goip.line}")
    private String goipLine ;

    @Value("${sim.om.ussdLong}")
    private String                  omRequest     ;
    @Value("${sim.om.pin}")
    private String                  omSimPin      ;


    public OMScheduled( GoipService goipService, WebClient.Builder webClientBuilder) {
        this.goipService      = goipService;
        this.webClientBuilder = webClientBuilder;
    }

    @Scheduled( fixedDelayString = omFixedRate )
    @Synchronized
    public void initierPaiement() {
        log.debug("fixedDelay");
        getPaiement().subscribe(
                p -> {
                    log.info(p.toString());
                    try {
                        initier(p);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        p.setEtat(ATTENTE);
                        updatePaiement(p).subscribe();
                        e.printStackTrace();
                    }
                }
        );
    }

    Flux<Paiement> getPaiement(){
        return webClientBuilder
                .baseUrl(baseUrl)
                .build()
                .get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path("/paiement/scheduler")
                                .queryParam("canal","ORANGEMONEY")
                                .queryParam("etatPaiement","ATTENTE")
                                .build()
                )
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse -> {
                            log.error(clientResponse.statusCode().toString());
                            return   clientResponse
                                    .bodyToMono(WebClientResponseException.class)
                                    .flatMap(Mono::error);
                        }
                )
                .bodyToFlux(Paiement.class);
    }

    Mono<Object> updatePaiement(Paiement paiement){
        log.info("update envoie {}", paiement);
        return webClientBuilder
                .baseUrl(baseUrl)
                .build()
                .put()
                .uri("/paiement")
                .bodyValue(paiement)
                .retrieve()
                .onStatus(HttpStatus::isError,
                        clientResponse -> {
                            log.error(clientResponse.statusCode().toString());
                            return   clientResponse
                                    .bodyToMono(WebClientResponseException.class)
                                    .flatMap(Mono::error);
                        }
                )
                .bodyToMono(Object.class);
    }

    @Synchronized
    void initier(Paiement paiement) throws Exception {
        String request = omRequest;
        request = request.replace("telephone",paiement.getClient().getNumeroCompte());
        request = request.replace("montant",paiement.getMontant().toString());
        request = request.replace("OMPIN", omSimPin);

        goipService.send(request,paiement.getId());
        String status = getStatus(paiement.getId());

        if(status.contains("confirm"));{
            goipService.send("1",paiement.getId());
            status = getStatus(paiement.getId());
        }

        if(status.contains("reussi"))
            paiement.setEtat(INITIER);
        else {
            paiement.setEtat(ECHEC);
            paiement.setCommentaire(status);
        }

        goipService.exit(paiement.getId());
        getStatus(paiement.getId());
        updatePaiement(paiement).subscribe();
        log.info("fin initier {}",paiement.getId());
    }

    String getStatus(Long id) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document ;
        int compteur  = 0;
        Element eElement;
        do{
            compteur++;

            if (compteur > 10)
                throw new Exception("delai d'attente depasse");

            Thread.sleep(3000);
            document = builder.parse(new ByteArrayInputStream(
                    goipService.status().getBytes(StandardCharsets.UTF_8)
            ));
            NodeList nodeList = document.getElementsByTagName("send-sms-status");
            eElement= (Element) nodeList.item(0);
        }
        while(eElement.getElementsByTagName("status"+goipLine).item(0).getTextContent().equals("STARTED")
            & eElement.getElementsByTagName("id"+goipLine).item(0).getTextContent().equals(id)
        );

        return  eElement.getElementsByTagName("error"+goipLine).item(0).getTextContent();
    }
}
