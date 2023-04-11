package com.example.procurator.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@RequiredArgsConstructor
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoFoundException extends RuntimeException{

    private String message;

    public NoFoundException(String message, Throwable cause){
        super(message, cause);
    }

    public NoFoundException(String message) {

        this.message = message;
    }
}
