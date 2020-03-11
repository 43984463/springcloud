package com.sherlock.springcloud.controller;

import com.sherlock.springcloud.entities.CommonResult;
import com.sherlock.springcloud.entities.Payment;
import com.sherlock.springcloud.service.paymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther Sherlock
 * @date 2020/3/9 19:08
 * @Description: 为了记录方法的调用，可以增加1个切面并定义一个注解，标注于需要进行记录的方法来获取该方法的执行内容，参数，时间等
 */
@RestController
@Slf4j
public class paymentController {

    @Autowired
    private paymentService paymentService;

    @Value("${server.port}")
    private String port;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 在执行远程调用时需要添加 @RequestBody 注解来传递对象
     * @param payment
     * @return
     */
    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("执行结果：" + result);

        if (result > 0){
            return new CommonResult(200, "插入payment成功在port:" + port, result);
        } else {
            return new CommonResult(444, "插入payment失败在port:" + port, null);
        }
    }

    /**
     *
     * @param paymentId
     * @return
     */
    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long paymentId){
        Payment payment = paymentService.getPaymentById(paymentId);
        log.info("执行结果：" + payment);

        if (payment != null){
            return new CommonResult(200, "查询payment成功在port:" + port, payment);
        } else {
            return new CommonResult(444, "查询payment失败在port:" + port, null);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery(){
        // 获取服务
        List<String> services = discoveryClient.getServices();
        for (String service:services) {
            log.info(service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PROVIDER-PAYMENT");
        for (ServiceInstance instance : instances) {
            log.info(instance.getHost() + instance.getPort() + instance.getInstanceId());
        }
        return this.discoveryClient;
    }

    @GetMapping("/payment/discovery2")
    public Object discovery2() {
        // 获取服务
        List<String> services = discoveryClient.getServices();
        if (services.size() > 0) {
            services.stream()
                    .forEach(service -> {
                        log.info("service name is:" + service);
                        discoveryClient.getInstances(service).stream()
                                .forEach(instance -> {
                                    log.info("instance info:" + instance.getHost() + " " + instance.getPort() + " " + instance.getInstanceId());
                                });
                    });
        }
        return this.discoveryClient;
    }
}