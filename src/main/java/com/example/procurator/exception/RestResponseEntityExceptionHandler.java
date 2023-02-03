package com.example.procurator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NoFoundException.class})
    public ResponseEntity<Object> handlerNotFoundException
            (NoFoundException noFoundException)
    {
        Exception exception = new Exception(
                noFoundException.getMessage(),
                noFoundException.getCause(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {AlreadyExistException.class})
    public ResponseEntity<Object> handlerAlreadyExistsEception
            (AlreadyExistException alreadyExistException){
        Exception exception = new Exception(
                alreadyExistException.getMessage(),
                alreadyExistException.getCause(),
                HttpStatus.FOUND
        );
        return new ResponseEntity<>(exception, HttpStatus.FOUND);
    }
}
