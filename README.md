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
 