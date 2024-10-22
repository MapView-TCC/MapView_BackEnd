package com.MapView.BackEnd.infra.Config.Secutiry;


import com.MapView.BackEnd.serviceImp.UserLogServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private final UserLogServiceImp userLogImp;

    public LoggingFilter(UserLogServiceImp userLogImp) {
        this.userLogImp = userLogImp;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        filterChain.doFilter(request,response);
        System.out.println("=====-=-=-=-=-=-=-=-==-=-=-ENTROU =-==-=-=-=-=-=-=-=-=-=-=-=-=-=-="
        );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();  // Obtenção do JWT
            String email = jwt.getClaimAsString("email");  // Email do usuário

            // Captura a ação do usuário (endpoint e método)
            String action = request.getMethod() + " " + request.getRequestURI();
            String recordId = "qwe";

            String headers =  "null";



            // Registrar log da ação no banco
            this.userLogImp.logger(email, action, headers);
        }

    }
}
