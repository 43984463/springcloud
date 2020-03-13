package com.sherlock.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @auther Sherlock
 * @date 2020/3/13 14:54
 * @Description:
 */

/**
 * Config 3344是作为项目与github的连接方。读取github对应项目分支地址下的信息
 * config-3344.com 需要在host文件中配置代表localhost
 * http://config-3344.com:3344/master/config-dev.yml
 *
 * curl -X POST "http://localhsot:3344/actuator/bus-refresh"
 * 作为总线进行广播，将bus-refresh发布到rabbitMQ中，发送之后全部进行接收
 *
 * curl -X POST "http://localhsot:3344/actuator/bus-refresh/cloud-config-client:3355"
 * 作为总线进行广播，将bus-refresh发布到rabbitMQ中，并只给微服务名称为cloud-config-client并且端口为3355的服务进行推送
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigCenterMain3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterMain3344.class, args);
    }
}
