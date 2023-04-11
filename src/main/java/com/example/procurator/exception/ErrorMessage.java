package com.example.procurator.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    HttpStatus status;
    String message;
    Date dateTime;
}
