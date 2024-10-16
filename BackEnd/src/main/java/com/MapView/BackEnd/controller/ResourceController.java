package com.MapView.BackEnd.controller;


import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class ResourceController {

    @GetMapping("/resource")
    @CrossOrigin(origins = "http://localhost:5173")
    public String getResourceString(@AuthenticationPrincipal Jwt jwt) {
        String userName = jwt.getClaimAsString("name");

        return "Recurso Protegido Acessado por: " + userName;
    }
}
