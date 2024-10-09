package com.MapView.BackEnd.infra.Exception;

public class OperativeFalseException extends RuntimeException {
    public OperativeFalseException(String message){
        super(message);
    }
}
