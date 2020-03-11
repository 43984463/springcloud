package com.sherlock.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther Sherlock
 * @date 2020/3/11 21:27
 * @Description:
 */
@Configuration
public class FeignLogConfig {

    @Bean
    public Logger.Level FeignLoggerLevel (){
        return Logger.Level.FULL;
    }

}
