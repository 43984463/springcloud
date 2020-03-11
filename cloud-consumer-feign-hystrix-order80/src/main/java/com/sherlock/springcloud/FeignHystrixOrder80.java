package com.sherlock.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @auther Sherlock
 * @date 2020/3/12 0:33
 * @Description:
 */

@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class FeignHystrixOrder80 {
    public static void main(String[] args) {
        SpringApplication.run(FeignHystrixOrder80.class,args);
    }
}
