package freelance.paiement.donne.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {

        http    .csrf()
                .disable()
                .authorizeRequests()
                //.antMatchers(HttpMethod.GET, "/user/info", "/api/foos/**")
                //.hasAuthority("SCOPE_read")
                //.antMatchers(HttpMethod.POST, "/api/foos")
                //.hasAuthority("SCOPE_write")
                .antMatchers("/v3/api-docs/*","/v3/api-docs","/swagger-config", "/swagger*/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt()
        ;
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // Convert realm_access.roles claims to granted authorities, for use in access decisions
        converter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
        return converter;
    }
}
