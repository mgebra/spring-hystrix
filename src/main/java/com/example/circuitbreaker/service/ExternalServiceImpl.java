package com.example.circuitbreaker.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalServiceImpl implements ExternalService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${external.service.url}")
    private String externalServiceUrl;

    @HystrixCommand(
            commandKey = "echoAndSleepCommandKey"
    )
    @Override
    public String callEchoService(String data) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                String.format("%s/api/echo?data={data}", externalServiceUrl),
                String.class,
                data);

        return responseEntity.getBody();
    }

    @HystrixCommand(
            commandKey = "echoAndSleepCommandKey",
            fallbackMethod = "defaultSleepService"
    )
    @Override
    public Integer callSleepService(Integer seconds) {
        ResponseEntity<Integer> responseEntity = restTemplate.getForEntity(
                String.format("%s/api/sleep?seconds={seconds}", externalServiceUrl),
                Integer.class,
                seconds);

        return responseEntity.getBody();
    }

    public Integer defaultSleepService(Integer seconds) {
        if (seconds > 5) {
            throw new RuntimeException();
        }

        return 0;
    }

    @HystrixCommand(
            commandKey = "callErrorService",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            }
    )
    @Override
    public Boolean callErrorService(boolean throwException) {
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(
                String.format("%s/api/error?throwException={throwException}", externalServiceUrl),
                Boolean.class,
                throwException);

        return responseEntity.getBody();
    }
}
