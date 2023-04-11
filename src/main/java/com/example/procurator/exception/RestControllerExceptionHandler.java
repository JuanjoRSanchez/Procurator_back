package com.example.procurator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler(value = {AlreadyExistException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> alreadyExistException(AlreadyExistException ex) {

        ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                new Date());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoFoundException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> noFoundException(NoFoundException ex) {

        ErrorMessage error = new ErrorMessage(HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                new Date());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {ServiceException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> globalServiceExceptionHandler(ServiceException ex) {

        ErrorMessage error = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                new Date());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
