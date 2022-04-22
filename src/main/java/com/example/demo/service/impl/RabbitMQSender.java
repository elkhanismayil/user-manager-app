package com.example.demo.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitMQSender {

    private final AmqpTemplate rabbitTemplate;

    @Value("${user.manager.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${user.manager.rabbitmq.routingKey}")
    private String routingKeyName;

    public void sendToQueue(){

    }
}
