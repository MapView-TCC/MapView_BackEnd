package com.MapView.BackEnd.dtos;

import org.springframework.security.oauth2.jwt.Jwt;

public record TokenDetailsDTO(String id_token) {

    public TokenDetailsDTO(Token token){
        this(token.getId_token());
    }
}
