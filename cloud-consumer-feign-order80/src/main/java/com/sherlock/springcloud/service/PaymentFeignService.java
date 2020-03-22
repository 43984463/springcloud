package com.sherlock.springcloud.service;

import com.sherlock.springcloud.entities.CommonResult;
import com.sherlock.springcloud.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther Sherlock
 * @date 2020/3/11 19:04
 * @Description:
 */
@Service
@FeignClient(value = "cloud-provider-payment")
public interface PaymentFeignService {

    /**
     * 注解的路径需要和服务提供方(cloud-provider-payment)的路径一致，参数也需要一致
     * @see com.sherlock.springcloud.controller.paymentController#getPaymentById(java.lang.Long)
     * @param id
     * @return
     */
    @GetMapping("/payment/get/{id}")
    CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);

    @GetMapping("/payment/zipkin")
    String zipkinPayment();
}
