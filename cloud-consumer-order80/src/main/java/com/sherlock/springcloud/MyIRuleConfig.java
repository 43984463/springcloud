package com.sherlock.springcloud;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther Sherlock
 * @date 2020/3/11 16:04
 * @Description: 包含重新定义负载均衡算法的bean不能在主配置可以扫描的包下（但是这个位置就是在可以扫描的包下啊，为什么测试是可以，算了，按照实际测试结果看）
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
