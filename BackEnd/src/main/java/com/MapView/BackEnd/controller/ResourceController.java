package com.MapView.BackEnd.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/resource")
    @CrossOrigin(origins = {"http://localhost:3000","http://localhost:4200"})
    public String getResourceString(@AuthenticationPrincipal Jwt jwt) {
        String userName = jwt.getClaimAsString("name");

        return "Recurso Protegido Acessado por: " + userName;
    }

}
