springCloud尚硅谷学习视频，B站地址 https://www.bilibili.com/video/av93813318

工具

下载[MindManager 2020](http://dwnld.mindjet.com/stubs/Builds/MindManager2020/20_0_334/64Bit/MindManager%202020.msi)

激活码
```text
2019: MP19-777-APE8-1162-BD8E

2020: MP20-345-DP56-7778-919A
```

# 第一天记录
1.在使用cloud-provider-payment8001访问com.sherlock.springcloud.controller.paymentController.create时，本身测试post接口是没有问题的，  <br>
但是在使用cloud-consumer-order80远程调用访问时 <br>
com.sherlock.springcloud.controller.OrderController.create(com.sherlock.springcloud.entities.Payment)  <br>
   -->cloud-provider-payment8001的com.sherlock.springcloud.controller.paymentController.create,会出现payment对象为空的情况， <br>
此时需要在cloud-provider-payment8001的com.sherlock.springcloud.controller.paymentController.create方法参数(Payment payment)添加注解@requestBody <br> 
修改为public CommonResult create(@RequestBody Payment payment),此时调用则可以传递Payment对象.  <br>

2.cloud-provider-payment8001项目的application.yml中添加mybatis.configuration.log-impl: org.apache.ibatis.logging.stdout.StdOutImpl  <br>
可以使mybatis在控制台打印sql，并且使mybatis log plugin插件有效(方便查看mybatis sql). <br>

3.配置spring.datasource.druid.validation-query: SELECT 1 FROM DUAL  # 测试连接,不配置则控制台会打印一个ERROR LOG.但是并不影响使用. <br>

# 第二天记录
在80端口的eureka订单服务，在com.sherlock.springcloud.controller.OrderController中当服务提供者为集群时使用localhost:8001用服务提供者的名称代替cloud-provider-payment, <br> 
并在 com.sherlock.springcloud.config.ApplicationContextConfig.restTemplate方法处开启@LoadBalanced负载均衡。 <br>
Eureka客户端配置
eureka.instance.instance-id  #Eureka客户端在eureka注册中心显示的名称 <br>
eureka.instance.lease-renewal-interval-in-seconds #Eureka客户端向服务端发送心跳的时间间隔，单位为秒(默认30S) <br>
eureka.instance.lease-expiration-duration-in-seconds #Eureka服务端在收到最后一次心跳后等待的时间上限，单位为秒(默认90S)，超时则会删除服务 <br>

Eureka服务端配置
eureka.server.enable-self-preservation #Eureka是否开启自我保护机制 <br>
eureka.server.eviction-interval-timer-in-ms #Eureka清理没有心跳的服务的时间，默认毫秒 <br>

# 第三天记录
## 使用ribbon+openfeign实现服务的负载均衡
**openfeign默认超时时间为1S**
① cloud-consumer-feign-order80#com.sherlock.springcloud.controller.OrderController#getPayment。 <br>
② com.sherlock.springcloud.service.PaymentFeignService.getPaymentById。 <br>
③ com.sherlock.springcloud.FeignOrder80 开启openFeign @EnableFeignClients。 <br>
④ com.sherlock.springcloud.service.PaymentFeignService 使用@FeignClient(value = "cloud-provider-payment")指定调用注册中心中的服务。 <br>


# 第四天记录
## 使用hystrix+feign进行服务容错
### 一、 某个特定方法的服务容错
参考com.sherlock.springcloud.service.PaymentService.paymentInfo_TimeOut_OK或者 <br>
com.sherlock.springcloud.controller.OrderController#paymentInfo_TimeOut_OK <br>
hystrix 使用 <br>
注解@HystrixCommand表示服务降级容错，既可以方法服务提供端，也可以放在服务调用端，一般配置在服务调用端，使用时请 ++ fallBack Method；<br>
### 二、 全局方法的服务容错
① 在类上添加注解 @DefaultProperties(defaultFallback = "PaymentInfo_TimeOut_Global_Handler") 并配置默认的fallback方法. <br>
② 在需要进行服务降级的方法上添加 @HystrixCommand 注解(如果配置了属性则优先使用该属性里面的配置). <br>
③ 添加在 @DefaultProperties(defaultFallback = "PaymentInfo_TimeOut_Global_Handler")注解中的PaymentInfo_TimeOut_Global_Handler方法来作为默认的fallback方法. <br>

### 三、 在服务调用端的service实现里面的所有方法来实现容错
在com.sherlock.springcloud.service.PaymentFeignHystrixService中添加实现类并在 <br>
@FeignClient(value = "cloud-provider-hystrix-payment", fallback = PaymentFeignFallBackServiceImpl.class)注解中将其配置为fallback方法。 <br>
需要将Impl假如到spring容器中(添加@Component注解)，此时出错则调用PaymentFeignFallBackServiceImpl中对应的方法。

## 使用hystrix进行服务熔断
```java
@HystrixCommand(
            fallbackMethod = "paymentCircuitBreaker_fallback",  //熔断之后的兜底方法
            commandProperties = {
                @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),// 时间窗口期/时间范围
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")// 失败率达到多少后跳闸
             }
    )
```

**@HystrixCommand注解的commandProperties可以配置的属性参考com.netflix.hystrix.HystrixCommandProperties**

## 使用gateway进行网关(route,predicate,filter)
### 使用application.yml配置文件配置
cloud-gateway-gateway9527/src/main/resources/application.yml <br>

网关之前的服务cloud-provider-payment8001 访问地址为http://localhost:8001/payment/get/1 <br>
添加网关注册并配置路径等之后http://localhost:9527/payment/get/1 <br>
### 使用注册bean的形式构建
cloud-gateway-gateway9527 -> com.sherlock.springcloud.config.GatewayConfig 
### 使用配置文件进行动态路由
application.yml -> spring.cloud.gateway.discovery.locator.enabled: true # 开启从注册中心动态创建路由的功能，利用微服务名称进行路由 (可如下配置) <br>
spring.cloud.gateway.routes[0].uri: lb:CLOUD-PROVIDER-PAYMENT
### 使用过滤器拦截请求
使用实现了org.springframework.cloud.gateway.filter.GlobalFilter接口的组件 <br>
重写org.springframework.cloud.gateway.filter.GlobalFilter.filter方法来拦截或者过滤请求。

# 第五天记录
## 使用config center读取github对应项目分支地址下的信息（config统一管理项目的配置文件）
cloud-config-center-3344 -> com.sherlock.springcloud.ConfigCenterMain3344

## 使用config client(3355)读取3344对应项目分支地址下的信息
配置文件名称为bootstrap.yml

### 3355通过3344读取github上对应路径，版本上的某个文件的信息
```yaml
spring:
    application:
         name: cloud-config-client
    cloud:
         config:
            #读取cloud-config-center-3344的分支
            label: master
            #读取cloud-config-center-3344的文件名
            name: config
            #读取cloud-config-center-3344的环境
            profile: dev
            #读取cloud-config-center-3344的地址
            uri: http://localhost:3344
```

### 3355不用重启通过3344动态读取
① 添加依赖 spring-boot-starter-actuator <br>
② bootstrap.yml 添加management.endpoints.web.exposure.include: "*" <br>
③ 在配置类上添加自动刷新注解@RefreshScope <br>
④ 运维在修改github信息之后发送post请求 curl -X POST "http://localhsot:3355/actuator/refresh" (需要下载curl命令) <br>

## 使用spring cloud stream进行消息的发送
**(相当于JPA之于底层数据库框架)**
spring cloud stream 暂时只支持rabbitMQ和kafka <br>
使用docker启动的rabbitMQ连接项目是报错 org.springframework.amqp.AmqpConnectException: java.net.ConnectException: Connection refused: connect
但是不影响使用，找了方法没修改成功，暂时不影响。

### 创建发送消息
application.yml -> spring.cloud.stream.bindings.output.destination: studyExchange # 表示要使用的Exchange名称定义(接收的时候也通过这个名称进行查找) <br>
①com.sherlock.springcloud.service.impl.IMessageProviderImpl 
添加注解@EnableBinding(Source.class) //定义消息的推送 <br>
②@Autowired
private MessageChannel output; //消息发送管道 <br>
③output.send(MessageBuilder.withPayload(message).build());  //发送消息到MQ或者kafka

### 创建接收消息
application.yml -> spring.cloud.stream.bindings.input.destination: studyExchange # 表示要使用的Exchange名称定义(接收的时候也通过这个名称进行查找) <br>
com.sherlock.springcloud.component.ReceiveMessageListenerComponent <br>
①添加注解@EnableBinding(Sink.class)并将此组件添加到spring容器中 <br>
②
@StreamListener(Sink.INPUT) <br>
     public void inputMessage(Message<String> message){ <br>
         log.info("消费者01；port：" + port + ", message :" + message.getPayload()); <br>
     }

## 解决多个消息消费方重复消费问题(8802和8803消费来自8801的消息)
在application.yml中添加group并命名相同，则为相同组，相同组消息的消费是竞争关系。 <br>
application.yml -> spring.cloud.stream.bindings.input.group <br>
**添加组信息之后，假如消息消费方宕机，重启之后仍然可以消费宕机期间的消息，但是没有分组的不行**

## zipkin(进行微服务调用链路的监控,默认端口9411)
http://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/ <br>

## Nacos 
**官网地址**： https://nacos.io/zh-cn/docs/cluster-mode-quick-start.html
下载之后使用cmd命令启动nacos-server-1.2.0\nacos\bin\startup.cmd(Window环境) <br>
访问 http://127.0.0.1:8848/nacos 查看控制台 <br>
spring.cloud.nacos.discovery 可以配置的参数都在 com.alibaba.cloud.nacos.NacosDiscoveryProperties里面看

**错误描述**

Description:

    Field registration in org.springframework.cloud.client.serviceregistry.ServiceRegistryAutoConfiguration$ServiceRegistryEndpointConfiguration required a single bean, but 2 were found:
    - nacosRegistration: defined by method 'nacosRegistration' in class path resource [com/alibaba/cloud/nacos/NacosDiscoveryAutoConfiguration.class]
    - eurekaRegistration: defined in BeanDefinition defined in class path resource [org/springframework/cloud/netflix/eureka/EurekaClientAutoConfiguration$RefreshableEurekaClientConfiguration.class]
**参考的类**

```java
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties
@ConditionalOnClass(EurekaClientConfig.class)
@Import(DiscoveryClientOptionalArgsConfiguration.class)
@ConditionalOnProperty(value = "eureka.client.enabled", matchIfMissing = true)
@ConditionalOnDiscoveryEnabled
@AutoConfigureBefore({ NoopDiscoveryClientAutoConfiguration.class,
        CommonsClientAutoConfiguration.class, ServiceRegistryAutoConfiguration.class })
@AutoConfigureAfter(name = {
        "org.springframework.cloud.autoconfigure.RefreshAutoConfiguration",
        "org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration",
        "org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationAutoConfiguration" })
public class EurekaClientAutoConfiguration{
}
```

**解决方法:** 
在application.yml中添加属性eureka.client.enabled: false即可 <br>

### Nacos Config
在bootstrap.yml中配置之后，在Nacos注册中心添加配置。
* Data ID为 ${spring.application.name}-${spring.profile.active}.${spring.cloud.nacos.config.file-extension} 需要完全按照这个格式。 <br>
namespace > group > dataId

### Nacos 内置数据库derby切换到mysql
**①.在mysql中创建对应数据库**
 Naocs文件夹下的nacos-server-1.2.0\nacos\conf\nacos-mysql.sql 来创建保存Nacos配置信息的数据库。 <br>
**②.修改Nacos连接数据库的信息**
Naocs文件夹下的nacos-server-1.2.0\nacos\conf\application.properties添加数据库连接信息。(参考nacos-server-1.2.0\nacos\conf\application.properties.example)

## Sentinel
```text
QPS每秒查询率(Query Per Second) 
```
**参考文档地址**
https://github.com/alibaba/Sentinel/wiki/%E4%BB%8B%E7%BB%8D

## Seata
**github地址**
https://github.com/seata/seata <br>
**参考文档地址**
https://seata.io/zh-cn/