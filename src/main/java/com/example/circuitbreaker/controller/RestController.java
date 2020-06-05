package com.example.circuitbreaker.controller;

import com.example.circuitbreaker.service.ExternalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private ExternalService externalService;

    @GetMapping("echo")
    public ResponseEntity<String> echo(String data) {
        String response = externalService.callEchoService(data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("sleep")
    public ResponseEntity<Integer> sleep(Integer seconds) throws InterruptedException {
        Integer response = externalService.callSleepService(seconds);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("error")
    public ResponseEntity<Boolean> error(boolean throwException) throws InterruptedException {
        Boolean response = externalService.callErrorService(throwException);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<Boolean> create(boolean throwException) throws InterruptedException {
        Boolean response = externalService.callErrorService(throwException);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
