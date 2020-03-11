package com.sherlock.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @auther Sherlock
 * @date 2020/3/11 19:00
 * @Description:
 */
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class FeignOrder80 {
    public static void main(String[] args) {
        SpringApplication.run(FeignOrder80.class, args);
    }
}
