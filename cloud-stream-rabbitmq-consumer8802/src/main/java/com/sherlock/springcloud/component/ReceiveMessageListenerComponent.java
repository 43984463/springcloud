package com.sherlock.springcloud.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * @auther Sherlock
 * @date 2020/3/13 18:58
 * @Description:
 */
@Component
@EnableBinding(Sink.class)
@Slf4j
public class ReceiveMessageListenerComponent {

    @Value("${server.port}")
    private String port;

    @StreamListener(Sink.INPUT)
    public void inputMessage(Message<String> message){
        log.info("消费者01；port：" + port + ", message :" + message.getPayload());
    }

}
