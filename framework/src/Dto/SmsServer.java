package Dto;

import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.time.LocalDateTime;

@Builder
public class SmsServer {
    private final static Logger log = LoggerFactory.getLogger(SmsServer.class);

    @Async
    public void read(int port, String password, WebClient.Builder callBackClientBuilder, String callbackUrl, String callbackPath ) throws SocketException {
         DatagramSocket socket = new DatagramSocket(port);

         byte[] buf = new byte[2048];

         String[] data;
         String[] protocol;
         String reponse = " ";
         while (true) {
             try {
                 log.info("waiting for data on port " + socket.getLocalPort());
                 DatagramPacket packet = new DatagramPacket(buf, buf.length);
                 socket.receive(packet);

                 log.info(packet.getAddress().toString());

                 String received = new String(packet.getData(), 0, packet.getLength());
                 log.info(received);

                 data = received.split(";");

                 if(data[2].split(":")[1].equals(password)){
                    protocol = data[0].split(":");
                 if (protocol[0].equals("req")) {
                     reponse = data[0] + ";status:200;" + "\n";
                 }
                 if (protocol[0].equals("RECEIVE")) {
                     reponse = protocol[0] + " " + protocol[1] + " OK" + "\n";
                     send(data, callBackClientBuilder,callbackUrl,callbackPath).subscribe(
                             s -> log.info(" {}", s)
                     );
                 }
                     packet.setData(reponse.getBytes());
                     socket.send(packet);
                 }
             } catch (IOException ioException) {
                 ioException.printStackTrace();
             }
         }
     }

     Mono<String> send(String[] data, WebClient.Builder callBackClientBuilder, String baseUrl, String path){
        return callBackClientBuilder
                .baseUrl(baseUrl)
                .build()
                .get()
                .uri(
                        uriBuilder -> uriBuilder
                                .path(path)
                                .queryParam("expediteur",data[3].split(":")[1])
                                .queryParam("sim",data[1].split(":")[1])
                                .queryParam("message",data[4].split(":",2)[1])
                                .queryParam("time", LocalDateTime.now().toString())
                                .build()
                )
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        clientResponse ->
                        {
                            log.error("error {}",clientResponse);
                        return     Mono.empty();
                        }
                )
                .onStatus(HttpStatus::is4xxClientError,
                        clientResponse -> {
                            log.error("error code {} ",clientResponse.statusCode());
                            return Mono.empty();
                        }
                )
                .bodyToMono(String.class)
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }


}
