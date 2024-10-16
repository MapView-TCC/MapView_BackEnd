package com.MapView.BackEnd.infra;

import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.entities.UserRole;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.infra.Exception.NotFoundException;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.repository.UserRoleRepository;
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
@EnableMethodSecurity
public class SecurityConfig {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public SecurityConfig(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // URL do JWKs fornecida pelo Azure para validação do token
        String jwkSetUri = "https://login.microsoftonline.com/0ae51e19-07c8-4e4b-bb6d-648ee58410f4/discovery/v2.0/keys";
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/resource/**").hasRole("APRENDIZ")  // Verifica a role APRENDIZ
                        .requestMatchers("/ap1/v1/user/**").authenticated()   // Protege a rota /user
                        .anyRequest().authenticated())// Permite outras requisições
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));  // Configura JWT

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String email = jwt.getClaimAsString("email");  // Extrai o email do token
            System.out.println("Email extraído do token: " + email);  // Apenas para depuração
            return getRolesFromDatabase(email);  // Busca as roles do banco de dados
        });
        return converter;
    }

    // Busca as roles do banco de dados e as converte em GrantedAuthority
    private Collection<GrantedAuthority> getRolesFromDatabase(String email) {
        // Busca o usuário pelo email
        Users user = userRepository.findByEmail(email).orElse(null);
        System.out.println("Usuário encontrado: " +new UserDetailsDTO(user));  // Apenas para depuração

        // Verifica se o usuário foi encontrado
        if (user != null) {
            // Busca as roles do usuário usando o UserRoleRepository
            UserRole userRoles = userRoleRepository.findByUser(user).orElseThrow(() -> new NotFoundException("User not found"));

            // Cria a lista de authorities com base nas roles
            System.out.println(userRoles.getRole().getName());
            return List.of(new SimpleGrantedAuthority(userRoles.getRole().getName()));
        }

        // Retorna uma lista vazia se o usuário não tiver roles ou não for encontrado
        return List.of();
    }
}
