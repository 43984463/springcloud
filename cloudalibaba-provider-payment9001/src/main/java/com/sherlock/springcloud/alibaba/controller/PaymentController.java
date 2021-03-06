package com.sherlock.springcloud.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Sherlock
 * @date 2020/3/13 23:29
 * @Description:
 */
@RestController
public class PaymentController
{
    @Value("${server.port}")
    private String serverPort;

    /**
     *http://localhost:9001/payment/nacos/1
     * @param id
     * @return
     */
    @GetMapping(value = "/payment/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id)
    {
        return "nacos registry, serverPort: "+ serverPort+"\t id"+id;
    }
}
