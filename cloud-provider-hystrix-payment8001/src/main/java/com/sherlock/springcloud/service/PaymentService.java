package com.sherlock.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @auther Sherlock
 * @date 2020/3/11 23:28
 * @Description:
 */
@Service
public class PaymentService {

    public String paymentInfo_OK(Integer id) {
        return "线程池名称：" + Thread.currentThread().getName() + ", Payment OK; id =" + id;
    }

    /**
     * @param id
     * @return
     * @HystrixCommand 注解的 fallbackMethod属性表示作为调用方法的备用方法，当被调用方法出现异常或者错误则调用备用方法;
     * @HystrixProperty 作为本方法被调用时的一些属性补充，假如在本方法中sleep5S，
     * 现在配置为3000ms，则超过3000就当本方法错误并调用备用方法
     * 假如配置修改为6000ms，则为6000ms>5s,不会调用备用方法
     * <p>
     * 本注解@HystrixCommand表示服务降级容错，既可以方法服务提供端，也可以放在服务调用端，一般配置在服务调用端，使用时请 ++ fallBack Method；
     * 在服务端时需要配合主配置的@EnableCircuitBreaker注解，
     * 在调用端时请配合(feign consumer)主配置的@EnableHystrix注解，以及feign.hystrix.enabled: true使用
     * @see com.netflix.hystrix.HystrixCommandProperties#default_executionTimeoutInMilliseconds 默认超时时间
     *
     */
    @HystrixCommand(fallbackMethod = "PaymentInfo_TimeOut_Handler", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeOut_OK(Integer id) {

        //int i = 10/0;

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池名称：" + Thread.currentThread().getName() + ",  Payment TimeOut; id =" + id;
    }

    public String PaymentInfo_TimeOut_Handler(Integer id) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池名称：" + Thread.currentThread().getName() + ",  Payment TimeOut,Call FallBack Method; id =" + id;
    }

    /**
     * ==============================================以下为服务熔断====================================================
     */
    /**
     * 在10秒窗口期中10次请求有6次是请求失败的,断路器将起作用
     * 所有属性配置请参考 {@link com.netflix.hystrix.HystrixCommandProperties}
     *
     * @param id
     * @return
     */
    @HystrixCommand(
            fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),// 时间窗口期/时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")// 失败率达到多少后跳闸
    }
    )
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("*****id不能是负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() + "\t" + "调用成功,流水号:" + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return Thread.currentThread().getName() + "\t" + "id 不能负数,请稍后重试,o(╥﹏╥)o id:" + id;
    }

}
