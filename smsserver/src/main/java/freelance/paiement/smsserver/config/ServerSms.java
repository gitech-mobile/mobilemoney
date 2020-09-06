package freelance.paiement.smsserver.config;

import Dto.SmsServer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.SocketException;

@Component
public class ServerSms {
    private final WebClient.Builder webClientBuilder;
    private final String baseUrl = "http://donne";
    private final String callbackPath = "/sms/";

    @Scheduled(fixedDelay = 1000)
    void read(){
        try {
            SmsServer.builder().build().read(33433,"gitechgoip",webClientBuilder,baseUrl,callbackPath);
        } catch (
                SocketException e) {
            e.printStackTrace();
        }
    }

    public ServerSms(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
}
