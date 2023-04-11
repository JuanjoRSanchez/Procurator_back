package com.example.procurator.exception;

public class ServiceException extends RuntimeException{
    public ServiceException(String message){
        super(message);
    }
}
