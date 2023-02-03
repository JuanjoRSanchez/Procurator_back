package com.example.procurator.exception;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
public class Exception {

    private final String message;

    private final  Throwable throwable;

    private final HttpStatus status;

}
