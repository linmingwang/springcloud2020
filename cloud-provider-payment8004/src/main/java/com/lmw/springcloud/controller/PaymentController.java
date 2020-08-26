package com.lmw.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PaymentController {

    @Value("${server.port}")
    private String port;

    public String paymentZk () {
        return "springcloud with zookeeper:"+port+"\t"+ UUID.randomUUID().toString();
    }
}
