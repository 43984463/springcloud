package com.sherlock.springcloud.controller;

import com.sherlock.springcloud.entities.CommonResult;
import com.sherlock.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @auther Sherlock
 * @date 2020/3/9 21:37
 * @Description:
 */
@RestController
@Slf4j
public class OrderController {

    // 当服务提供者为1个时使用
    // public static final String PAYMENT_RUL = "http://localhost:8001";
    // 当服务提供者为集群时使用，localhost:8001用服务提供者的名称代替cloud-provider-payment,并在RestTemplate处开启@LoadBalanced负载均衡
    public static final String PAYMENT_RUL = "http://cloud-provider-payment";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        log.info("payment：" + payment);
        return restTemplate.postForObject(PAYMENT_RUL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getObjectById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_RUL + "/payment/get/" + id, CommonResult.class);
    }

    @GetMapping("/consumer/payment/getentity/{id}")
    public ResponseEntity<CommonResult> getEntityById(@PathVariable("id") Long id){
        return restTemplate.getForEntity(PAYMENT_RUL + "/payment/get/" + id, CommonResult.class);
    }
}