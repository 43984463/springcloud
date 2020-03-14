package com.sherlock.springcloud.alibaba.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Sherlock
 * @date 2020/3/14 20:20
 * @Description:
 */
@RestController
@Slf4j
public class FlowLimitController {

    /**
     * 需要访问1次之后才可以在流量监控 sentinel 中看到
     *http://localhost:8401/testA
     * @return
     */
    @GetMapping("/testA")
    public String testA() {
        return "------testA";
    }

    /**
     * http://localhost:8401/testB
     * @return
     */
    @GetMapping("/testB")
    public String testB() {
        log.info(Thread.currentThread().getName() + "\t" + "...testB");
        return "------testB";
    }

}