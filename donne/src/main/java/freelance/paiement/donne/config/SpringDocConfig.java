package freelance.paiement.donne.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SpringDocConfig {
    @Value("${app.auth_uri}")
    private  String auth_uri;

    @Bean
    public OpenAPI customOpenAPI() {
        OAuthFlows flows = new OAuthFlows();
        OAuthFlow flow = new OAuthFlow();

        flow.setAuthorizationUrl(auth_uri + "/protocol/openid-connect/auth");

        Scopes scopes = new Scopes();
        flow.setScopes(scopes);
        flows = flows.implicit(flow);

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("keycloak",
                        new SecurityScheme().type(SecurityScheme.Type.OAUTH2).flows(flows)))
                .info(new Info().title("Mobile Paiement")
                        .version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("keycloak",
                        Arrays.asList("read", "write")));
    }

}
