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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/resource/**").authenticated()  // Protege a rota
                        .anyRequest().permitAll())  // Permite outras requisições
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));  // Configuração JWT

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String email = jwt.getClaim("email");  // Extraindo o email do token
            Collection<? extends GrantedAuthority> authorities = getRolesFromDatabase(email);  // Buscando roles
            return authorities;
        });
        return converter;
    }

    // Implementação para buscar roles do banco de dados
    private Collection<? extends GrantedAuthority> getRolesFromDatabase(String email) {
        // Busca o usuário pelo email
        Users user = userRepository.findByEmail(email);

        // Verifica se o usuário foi encontrado
        if (user != null) {
            // Busca as roles do usuário usando o UserRoleRepository
            List<UserRole> userRoles = userRoleRepository.findByUser(user).orElse(null);

            // Converte a lista de UserRole em GrantedAuthority
            return userRoles.stream()
                    .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().getClass().getName()))  // Converte Role para GrantedAuthority
                    .collect(Collectors.toList());
        }

        // Retorna uma lista vazia se o usuário não tiver roles ou não for encontrado
        return List.of();
    }

}
