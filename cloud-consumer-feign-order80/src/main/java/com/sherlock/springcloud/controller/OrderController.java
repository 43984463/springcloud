package com.sherlock.springcloud.controller;

import com.sherlock.springcloud.entities.CommonResult;
import com.sherlock.springcloud.entities.Payment;
import com.sherlock.springcloud.service.PaymentFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Sherlock
 * @date 2020/3/11 19:09
 * @Description:
 */
@RestController
public class OrderController {

    @Autowired
    private PaymentFeignService paymentFeignService;

    /**
     * 服务调用端的 @GetMapping 路径只需要能找到服务调用端的service就可以
     * @param id
     * @return
     */
    @GetMapping("/payment/get/feign/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
       return paymentFeignService.getPaymentById(id);
    }

}
