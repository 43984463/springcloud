package com.sherlock.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther Sherlock
 * @date 2020/3/13 15:12
 * @Description:
 */
@RestController
@RefreshScope
public class ConfigClientController
{
    @Value("${config.info}")
    private String configInfo;

    /**
     * http://localhost:3366/configInfo
     * @return
     */
    @GetMapping("/configInfo")
    public String getConfigInfo()
    {
        return configInfo;
    }
}
