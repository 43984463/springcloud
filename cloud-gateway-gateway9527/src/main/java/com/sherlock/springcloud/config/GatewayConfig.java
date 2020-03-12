package com.sherlock.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @auther Sherlock
 * @date 2020/3/12 18:16
 * @Description:
 */
@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){

        /**
         * 使用访问 http://localhost:9527/github 代替
         * https://github.com/43984463/springcloud
         */
        RouteLocatorBuilder.Builder routes = builder.routes();
        return routes.route("path_route_sherlock",route -> route.path("/github").uri("https://github.com/43984463/springcloud")).build();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        return routes.route("path_route_atguigu", r -> r.path("/guonei").uri("https://news.baidu.com/guonei")).build();
    }

}
