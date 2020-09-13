package freelance.paiement.donne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient
public class DonneApplication {
    public static void main(String[] args) {
        SpringApplication.run(DonneApplication.class, args);
    }
}
