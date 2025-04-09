package org.example.analyticsservice.common.exceptions.handlers;

import org.example.analyticsservice.common.exceptions.EmptyResponseBodyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionsHandler {

    @Value("${debug}")
    private boolean isDebug = false;

    @ExceptionHandler(EmptyResponseBodyException.class)
    public ResponseEntity<String> handleEmptyResponseBodyException(EmptyResponseBodyException e) {
        String body = null;

        if(isDebug){
            body = "Received empty response body from another service: {%s}".formatted(e.toString());
        }

        return ResponseEntity.internalServerError().body(body);

    }
}
