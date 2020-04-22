package com.example.circuitbreaker.controller;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException e, WebRequest request) {
        e.printStackTrace();

        String bodyOfResponse = LocalDateTime.now().format(timeFormatter) + "</br>" + e.getMessage();
        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(HystrixRuntimeException.class)
    protected ResponseEntity<Object> handleHystrixException(RuntimeException e, WebRequest request) {
        e.printStackTrace();

        String bodyOfResponse = LocalDateTime.now().format(timeFormatter) + "</br>" + e.getMessage();
        return handleExceptionInternal(e, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
