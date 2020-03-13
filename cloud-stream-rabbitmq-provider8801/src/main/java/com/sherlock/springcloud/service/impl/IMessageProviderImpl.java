package com.sherlock.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.sherlock.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @auther Sherlock
 * @date 2020/3/13 17:43
 * @Description:
 */
@EnableBinding(Source.class)  //定义消息的推送
@Slf4j
public class IMessageProviderImpl implements IMessageProvider {

    @Autowired
    private MessageChannel output; //消息发送管道

    @Override
    public String send() {
        String message = IdUtil.simpleUUID();
        log.info("Send to RabbitMQ message :" + message + 8);
        output.send(MessageBuilder.withPayload(message).build());  //发送消息到MQ或者kafka
        return null;
    }

}

