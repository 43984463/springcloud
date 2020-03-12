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
① cloud-consumer-feign-order80#com.sherlock.springcloud.controller.OrderController#getPayment。 <br>
② com.sherlock.springcloud.service.PaymentFeignService.getPaymentById。 <br>
③ com.sherlock.springcloud.FeignOrder80 开启openFeign @EnableFeignClients。 <br>
④ com.sherlock.springcloud.service.PaymentFeignService 使用@FeignClient(value = "cloud-provider-payment")指定调用注册中心中的服务。 <br>


# 第四天记录
## 使用hystrix进行服务容错
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
在com.sherlock.springcloud.service.PaymentFeignHystrixService中添加实现类并在
@FeignClient(value = "cloud-provider-hystrix-payment", fallback = PaymentFeignFallBackServiceImpl.class)注解中将其配置为fallback方法。 <br>
需要将Impl假如到spring容器中(添加@Component注解)，此时出错则调用PaymentFeignFallBackServiceImpl中对应的方法。

## 使用hystrix进行服务熔断
@HystrixCommand(
            fallbackMethod = "paymentCircuitBreaker_fallback",  //熔断之后的兜底方法 <br>
            commandProperties = { <br>
                @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),// 是否开启断路器 <br>
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),// 请求次数 <br>
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),// 时间窗口期/时间范围 <br>
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")// 失败率达到多少后跳闸 <br>
             } <br>
    ) <br>
此注解可以配置的属性参考com.netflix.hystrix.HystrixCommandProperties <br>

## 使用gateway进行网关(route,predicate,filter)
### 使用application.yml配置文件配置
cloud-gateway-gateway9527/src/main/resources/application.yml <br>

网关之前的服务cloud-provider-payment8001 访问地址为http://localhost:8001/payment/get/1 <br>
添加网关注册并配置路径等之后http://localhost:9527/payment/get/1 <br>
### 使用注册bean的形式构建
cloud-gateway-gateway9527 -> com.sherlock.springcloud.config.GatewayConfig 
### 使用配置文件进行动态路由
application.yml -> spring.cloud.gateway.discovery.locator.enabled: true # 开启从注册中心动态创建路由的功能，利用微服务名称进行路由 <br>
spring.cloud.gateway.routes[0].uri: lb:CLOUD-PROVIDER-PAYMENT
### 使用过滤器拦截请求
使用实现了org.springframework.cloud.gateway.filter.GlobalFilter接口的组件 <br>
重写org.springframework.cloud.gateway.filter.GlobalFilter.filter方法来拦截或者过滤请求。

