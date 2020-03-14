package com.sherlock.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Sherlock
 * @date 2020/3/14 14:18
 * @Description:
 */
@RestController
@RefreshScope //支持Nacos的动态刷新读取配置功能。
public class ConfigClientController
{
    @Value("${config.info}")
    private String configInfo;

    /**
     *http://localhost:3377/config/info
     * @return
     */
    @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }
}
