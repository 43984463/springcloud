package com.sherlock.springcloud.service;

import com.sherlock.springcloud.service.serviceimpl.PaymentFeignFallBackServiceImpl;
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
/**
 * ①添加本接口的实现类并重写所以方法的fallback方法，并将其加入spring容器。
 * ②在@FeignClient注解中配置fallback（默认的fallback class）
 */
@FeignClient(value = "cloud-provider-hystrix-payment", fallback = PaymentFeignFallBackServiceImpl.class)
public interface PaymentFeignHystrixService {

    @GetMapping("/payment/hystrix/timeout/{id}")
    String paymentInfo_TimeOut_OK(@PathVariable("id") Integer id);

}
