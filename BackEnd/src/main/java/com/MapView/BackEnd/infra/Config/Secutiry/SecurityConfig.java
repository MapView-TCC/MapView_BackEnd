package com.MapView.BackEnd.infra.Config.Secutiry;

import com.MapView.BackEnd.dtos.User.UserDetailsDTO;
import com.MapView.BackEnd.entities.Users;
import com.MapView.BackEnd.repository.UserRepository;
import com.MapView.BackEnd.serviceImp.UserLogServiceImp;
import com.MapView.BackEnd.serviceImp.UserServiceImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    private  final UserLogServiceImp userLogImp;
    private  final UserServiceImp userServiceImp;


    @Value("${myapp.jwtUri}")
    private String jwkSetUri;

    public SecurityConfig(UserRepository userRepository, UserLogServiceImp userLogImp, UserServiceImp userServiceImp) {
        this.userRepository = userRepository;
        this.userLogImp = userLogImp;

        this.userServiceImp = userServiceImp;
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // URL do JWKs fornecida pelo Azure para validação do token
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/resource/**").authenticated()  // Verifica a role APRENDIZ
                        .requestMatchers(HttpMethod.POST,"/api/v1/equipment/**").hasAnyRole("MEIO_OFICIAL","INSTRUTOR","GESTOR")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/equipment/**").hasAnyRole("MEIO_OFICIAL","INSTRUTOR","GESTOR")
                        .requestMatchers("/api/v1/notifications").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/v1/trackingHistory/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/trackingHistory/filterbyenvironment").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/v1/trackingHistory").hasAnyRole("MEIO_OFICIAL","INSTRUTOR","GESTOR")
                        .requestMatchers(HttpMethod.GET,"/api/v1/environment/**").authenticated()
                        .requestMatchers(HttpMethod.PUT,"/api/v1/environment").hasAnyRole("MEIO_OFICIAL","INSTRUTOR","GESTOR")
                        .requestMatchers(HttpMethod.POST,"/api/v1/environment").hasAnyRole("MEIO_OFICIAL","INSTRUTOR","GESTOR")
                        .requestMatchers(HttpMethod.GET,"/api/v1/excel").hasAnyRole("MEIO_OFICIAL","INSTRUTOR","GESTOR")
                        .requestMatchers(HttpMethod.POST,"/api/v1/register").hasAnyRole("MEIO_OFICIAL","INSTRUTOR","GESTOR")
                        .requestMatchers(HttpMethod.POST,"/api/v1/registerEnvironment").hasAnyRole("MEIO_OFICIAL","INSTRUTOR","GESTOR")
                        .requestMatchers(HttpMethod.GET, "/api/v1/equipment/search").authenticated()
                        .requestMatchers(HttpMethod.GET,"/api/v1/equipmentresponsible/filter").hasAnyRole("MEIO_OFICIAL","INSTRUTOR","GESTOR")
                        .requestMatchers("/connect/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/credentials").permitAll()
                        .requestMatchers(HttpMethod.GET,"ap1/v1/permissions").hasAnyRole("INSTRUTOR","GESTOR")
                        .requestMatchers(HttpMethod.POST,"ap1/v1/permissions").hasAnyRole("INSTRUTOR","GESTOR")
                        .requestMatchers(HttpMethod.POST,"ap1/v1/permissions/decline").hasAnyRole("INSTRUTOR","GESTOR")
                        .requestMatchers(HttpMethod.POST,"ap1/v1/permissions/accept").hasAnyRole("INSTRUTOR","GESTOR")
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                        .requestMatchers("/api/v1/user/**").authenticated()   // Protege a rota /user
                        .anyRequest().authenticated())// Permite outras requisições

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .addFilterAfter(new LoggingFilter(userLogImp), BearerTokenAuthenticationFilter.class);  // Configura JWT

        return http.build();
    }
    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter(userLogImp); // userLogImp deve ser injetado corretamente
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String email = jwt.getClaimAsString("email");  // Extrai o email do token
            System.out.println("Email extraído do token: " + email);  // Apenas para depuração
            return getRolesFromDatabase(email,jwt);  // Busca as roles do banco de dados
        });
        return converter;
    }

    // Busca as roles do banco de dados e as converte em GrantedAuthority
    private Collection<GrantedAuthority> getRolesFromDatabase(String email, Jwt jwt) {

        Users user = userRepository.findByEmail(email).orElse(null);
        String name2 = jwt.getClaimAsString("name");
        user.setName(name2);
        System.out.println("Usuário encontrado: " +new UserDetailsDTO(user));


        if (user != null) {
            return List.of(new SimpleGrantedAuthority(user.getRole().getName()));
        }
        String name = jwt.getClaimAsString("name");
        Users entityUser = new Users(email,name);
        Users newUser = userServiceImp.userWithoutRole(entityUser);

        return List.of(new SimpleGrantedAuthority(newUser.getRole().getName()));
    }
}
