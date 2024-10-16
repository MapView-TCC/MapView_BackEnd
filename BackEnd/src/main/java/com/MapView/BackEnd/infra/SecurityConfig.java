package com.MapView.BackEnd.infra;

import com.MapView.BackEnd.entities.UserRole;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.repository.UserRoleRepository;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Substituto para @EnableGlobalMethodSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public SecurityConfig(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // Substitua "your-jwks-uri" pela URL do seu JWKs, que geralmente é fornecida pelo provedor de identidade (Azure)
        String jwkSetUri = "https://login.microsoftonline.com/0ae51e19-07c8-4e4b-bb6d-648ee58410f4/discovery/v2.0/keys";
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/resource/**").hasRole("APRENDIZ")
                        .requestMatchers("/ap1/v1/user/**").authenticated()  // Protege a rota
                        .anyRequest().permitAll())  // Permite outras requisições
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));  // Configuração JWT

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String email = jwt.getClaimAsString("email");  // Extraindo o email do token
            Collection<GrantedAuthority> authorities = getRolesFromDatabase(email);  // Buscando roles
            return authorities;
        });
        return converter;
    }

    // Alteração aqui: retorna Collection<GrantedAuthority>
    private Collection<GrantedAuthority> getRolesFromDatabase(String email) {
        // Busca o usuário pelo email
        Users user = userRepository.findByEmail(email);

        // Verifica se o usuário foi encontrado
        if (user != null) {
            // Busca as roles do usuário usando o UserRoleRepository
            List<UserRole> userRoles = userRoleRepository.findByUser(user).orElse(Collections.emptyList());
            for (UserRole r: userRoles){
                r.getRole()

            }

            // Converte a lista de UserRole em GrantedAuthority
            return userRoles.stream()
                    .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getClass().g)  // Converte Role para GrantedAuthority
                    .collect(Collectors.toList());
        }

        // Retorna uma lista vazia se o usuário não tiver roles ou não for encontrado
        return List.of();
    }
}
