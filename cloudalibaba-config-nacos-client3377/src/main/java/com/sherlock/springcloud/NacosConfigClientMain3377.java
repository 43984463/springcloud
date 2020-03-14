package com.sherlock.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @auther Sherlock
 * @date 2020/3/14 14:17
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosConfigClientMain3377 {
    public static void main(String[] args) {

        SpringApplication.run(NacosConfigClientMain3377.class, args);
    }
}
