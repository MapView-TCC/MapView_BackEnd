package com.MapView.BackEnd.dtos.Token;

public record TokenDetailsDTO(String id_token) {

    public TokenDetailsDTO(Token token){
        this(token.getId_token());
    }
}
