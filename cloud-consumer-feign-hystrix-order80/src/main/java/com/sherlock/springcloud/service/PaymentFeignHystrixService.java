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
@FeignClient(value = "cloud-provider-hystrix-payment")
public interface PaymentFeignHystrixService {

    @GetMapping("/payment/hystrix/timeout/{id}")
    String paymentInfo_TimeOut_OK(@PathVariable("id") Integer id);

}
