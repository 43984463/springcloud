package com.sherlock.springcloud;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther Sherlock
 * @date 2020/3/11 16:04
 * @Description:
 */
@Configuration
public class MyIRuleConfig {

    @Bean
    public IRule iRule(){
        return new RandomRule();
    }
}
