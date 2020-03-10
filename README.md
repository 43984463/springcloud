# 第一天记录
1.在使用cloud-provider-payment8001访问com.sherlock.springcloud.controller.paymentController.create时，本身测试post接口是没有问题的，
但是在使用cloud-consumer-order80远程调用访问时
com.sherlock.springcloud.controller.OrderController.create(com.sherlock.springcloud.entities.Payment)
   -->cloud-provider-payment8001的com.sherlock.springcloud.controller.paymentController.create,会出现payment对象为空的情况
此时需要在cloud-provider-payment8001的com.sherlock.springcloud.controller.paymentController.create方法参数(Payment payment)添加注解@requestBody
修改为public CommonResult create(@RequestBody Payment payment),此时调用则可以传递Payment对象.

2.cloud-provider-payment8001项目的application.yml中添加mybatis.configuration.log-impl: org.apache.ibatis.logging.stdout.StdOutImpl 
可以使mybatis在控制台打印sql，并且使mybatis log plugin插件有效(方便查看mybatis sql).

3.配置spring.datasource.druid.validation-query: SELECT 1 FROM DUAL  
测试连接,不配置则控制台会打印一个ERROR LOG.但是并不影响使用.

# 第二天记录
在80端口的eureka订单服务，在com.sherlock.springcloud.controller.OrderController中当服务提供者为集群时使用localhost:8001用服务提供者的名称代替cloud-payment-service,
并在 com.sherlock.springcloud.config.ApplicationContextConfig.restTemplate方法处开启@LoadBalanced负载均衡。