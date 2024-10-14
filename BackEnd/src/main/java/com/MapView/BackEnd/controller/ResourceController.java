package com.MapView.BackEnd.controller;


import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

public class ResourceController {
    @GetMapping("/resource")
    @CrossOrigin(origins = "http://localhost:3000")
    public String getResourceString(@AuthenticationPrincipal  jwt) {
        String userName = jwt("name");

        return "Recurso Protegido Acessado por: " + userName;
    }
}
