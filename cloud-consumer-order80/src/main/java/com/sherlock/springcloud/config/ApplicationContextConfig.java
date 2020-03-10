package com.sherlock.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @auther Sherlock
 * @date 2020/3/9 21:37
 * @Description:
 */
@Configuration
public class ApplicationContextConfig {

    @Bean
    // 开启负载均衡
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}