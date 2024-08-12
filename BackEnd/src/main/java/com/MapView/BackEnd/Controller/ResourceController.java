package com.MapView.BackEnd.Controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/resource")
    @CrossOrigin(origins = "http://localhost:5173")


    public String getResourceString(@AuthenticationPrincipal Jwt jwt){
        String username = jwt.getClaimAsString("name");
        String role = jwt.getClaimAsString("roles");


        return "Recurso Protegido Acessado por: "+ username+","+role;
    }
}
