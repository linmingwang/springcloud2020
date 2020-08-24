package com.lmw.springcloud.controller;

import com.lmw.springcloud.entities.CommonResult;
import com.lmw.springcloud.entities.Payment;
import com.lmw.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class PaymentController {

    @Resource
    private PaymentService service;

    @Value("${server.port}")
    private String port;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommonResult create (@RequestBody Payment payment) {
        int result = service.create(payment);
        if (result > 0) {
            return new CommonResult(200,"插入数据库成功,serverPort:" + port,result);
        } else {
            return new CommonResult(444,"插入数据库失败,serverPort:" + port,result);
        }
    }
    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById (@PathVariable("id") Long id) {
        Payment payment = service.getPaymentById(id);
        int age = 20;
        if (payment != null) {
            return new CommonResult(200,"查询成功,serverPort:" + port,payment);
        } else {
            return new CommonResult(444,"没有对应记录，查询ID：" + id,payment);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            log.info("*****element:" + element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("PROVIDER-PAYMENT-SERVICE");

        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return discoveryClient;
    }
}