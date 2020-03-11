package com.sherlock.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @auther Sherlock
 * @date 2020/3/11 23:28
 * @Description:
 */
@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id){
        return "线程池名称：" + Thread.currentThread().getName() + ", Payment OK; id =" + id;
    }

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
    @HystrixCommand(fallbackMethod = "PaymentInfo_TimeOut_Handler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeOut_OK(Integer id){

        //int i = 10/0;

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池名称：" + Thread.currentThread().getName() + ",  Payment TimeOut; id =" + id;
    }

    public String PaymentInfo_TimeOut_Handler(Integer id){
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池名称：" + Thread.currentThread().getName() + ",  Payment TimeOut,Call FallBack Method; id =" + id;
    }
}
