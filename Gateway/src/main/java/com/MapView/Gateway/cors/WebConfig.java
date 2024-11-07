package com.MapView.Gateway.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // permite todas as rotas
                .allowedOrigins("http://localhost:4200") // substitua pela URL do seu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // métodos permitidos
                .allowedHeaders("*"); // permite todos os cabeçalhos
    }
}
