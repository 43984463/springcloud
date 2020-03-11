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

    /**
     * 往spring容器中添加IRule来设置使用的负载均衡方式
     * @return
     */
    @Bean
    public IRule iRule(){
        return new RandomRule();
    }
}
