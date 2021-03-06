package com.sherlock.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;


/**
 * @auther Sherlock
 * @date 2020/3/9 21:22
 */
@SpringBootApplication
@EnableEurekaClient
/**
 * value为服务提供方的名称spring.application.name,configuration为自定义包含负载均衡设置的bean
  */
@RibbonClient(value = "cloud-provider-payment", configuration = {MyIRuleConfig.class})
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class,args);
    }
}