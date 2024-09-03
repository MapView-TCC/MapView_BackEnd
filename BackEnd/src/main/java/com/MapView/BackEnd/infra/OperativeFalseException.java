package com.MapView.BackEnd.infra;

public class OperativeFalseException extends RuntimeException {
    public OperativeFalseException(String message){
        super(message);
    }
}
