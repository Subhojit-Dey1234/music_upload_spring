package com.musicupload.music.clone.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomErrorHandling.class)
    public ResponseEntity<String> handleCustomerError(CustomErrorHandling ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(Exception ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error saving music");
    }
}
