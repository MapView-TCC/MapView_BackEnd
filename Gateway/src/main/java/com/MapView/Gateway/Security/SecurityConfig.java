package com.MapView.Gateway.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

import java.net.URI;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private ReactiveClientRegistrationRepository clientRegistrationRepository;


    @Value("${spring.security.oauth2.client.provider.azure.jwk-set-uri}")
    private String jwkSetUri;

    @Value("${spring.security.oauth2.client.provider.azure.logout}")
    private String Setlogout;

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable).cors(ServerHttpSecurity.CorsSpec::disable);

        // - Permite acesso público ao caminho "/login" e requer autenticação para qualquer outro caminho.

        http.authorizeExchange(conf -> conf
                        .pathMatchers("/login").permitAll()
                        .pathMatchers("/userinfo").authenticated()
                        .pathMatchers("/logout").permitAll()
                        .anyExchange().authenticated())

                // - Define o resolvedor de requisições de autorização.
                // - Redireciona para "/profile" após a autenticação bem-sucedida.

                .oauth2Login(conf -> conf
                        .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("http://localhost:4200/environment"))
                        .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("http://localhost:4200/")))
                .logout(logout -> logout
                        .logoutSuccessHandler((exchange, authentication) -> {
                            // Redireciona para o URL de logout do Azure
                            exchange.getExchange().getResponse().setStatusCode(HttpStatus.FOUND);
                            exchange.getExchange().getResponse().getHeaders().setLocation(URI.create(Setlogout));
                            return exchange.getExchange().getResponse().setComplete(); // Finaliza a resposta
                        }))


                // - Define o decodificador JWT para validar tokens de acesso.

                .oauth2ResourceServer(conf -> conf
                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder())))
                ;
        return http.build();
    }

    // Cria um bean para o decodificador de JWTs usando o URI do JWK SET que configuramos acima!.

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

}
