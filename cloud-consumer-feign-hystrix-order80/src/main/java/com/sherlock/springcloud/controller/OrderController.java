package com.sherlock.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sherlock.springcloud.service.PaymentFeignHystrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Sherlock
 * @date 2020/3/12 0:36
 * @Description:
 */
@RestController
/**
 * 设置全局默认fallback方法
 *
 * ① 在类上添加注解 @DefaultProperties(defaultFallback = "PaymentInfo_TimeOut_Global_Handler") 并配置默认的fallback方法
 * ② 在需要进行服务降级的方法上添加 @HystrixCommand 注解（如果配置了属性则优先使用该属性里面的配置）
 * ③ 添加在 @DefaultProperties(defaultFallback = "PaymentInfo_TimeOut_Global_Handler")注解中的PaymentInfo_TimeOut_Global_Handler方法来作为默认的fallback方法。
 */
//@DefaultProperties(defaultFallback = "PaymentInfo_TimeOut_Global_Handler")
public class OrderController {

    @Autowired
    private PaymentFeignHystrixService paymentFeignHystrixService;

    /**
     *
     * @HystrixCommand 注解的 fallbackMethod属性表示作为调用方法的备用方法，当被调用方法出现异常或者错误则调用备用方法;
     *
     * @HystrixProperty 作为本方法被调用时的一些属性补充，假如在本方法中sleep5S，
     * 现在配置为3000ms，则超过3000就当本方法错误并调用备用方法
     * 假如配置修改为6000ms，则为6000ms>5s,不会调用备用方法
     *
     * 本注解@HystrixCommand表示服务降级容错，既可以方法服务提供端，也可以放在服务调用端，一般配置在服务调用端，使用时请 ++ fallBack Method；
     * 在服务端时需要配合主配置的@EnableCircuitBreaker注解，
     * 在调用端时请配合(feign consumer)主配置的@EnableHystrix注解，以及feign.hystrix.enabled: true使用
     * @param id
     * @return
     */
    /*@HystrixCommand(fallbackMethod = "PaymentInfo_TimeOut_Handler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "1500")
    })*/
    //@HystrixCommand
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut_OK(@PathVariable("id") Integer id){
        return paymentFeignHystrixService.paymentInfo_TimeOut_OK(id);
    }

    public String PaymentInfo_TimeOut_Handler(Integer id){
        return "cloud-consumer-feign-hystrix-order80 的备用方法。";
    }

    public String PaymentInfo_TimeOut_Global_Handler(){
        return "cloud-consumer-feign-hystrix-order80 的全局备用方法。";
    }

}
