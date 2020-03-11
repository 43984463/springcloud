package com.sherlock.springcloud.controller;

import com.sherlock.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Sherlock
 * @date 2020/3/11 23:32
 * @Description:
 */
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentservice;

    @Value("${server.port}")
    private String port;

    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        return paymentservice.paymentInfo_OK(id);
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut_OK(@PathVariable("id") Integer id){
        return paymentservice.paymentInfo_TimeOut_OK(id);
    }
}
