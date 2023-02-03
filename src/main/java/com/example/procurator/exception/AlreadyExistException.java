package com.example.procurator.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
public class AlreadyExistException extends RuntimeException{

    private String message ;

    public AlreadyExistException(String message, Throwable cause){
        super(message, cause);
    }

    public AlreadyExistException(HttpStatus status){
        super(status.toString());
        this.message = status.toString() ;
    }

    public AlreadyExistException(String message) {
        this.message = message;
    }
}
