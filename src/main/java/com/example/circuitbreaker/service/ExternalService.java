package com.example.circuitbreaker.service;

public interface ExternalService {
    String callEchoService(String data);

    Integer callSleepService(Integer seconds);

    Boolean callErrorService(boolean throwException);
}
