package com.example.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${user.manager.rabbitmq.queue}")
    private String queueName;

    @Value("${user.manager.rabbitmq.exchange}")
    private String exchangeName;

    @Value("${user.manager.rabbitmq.routingKey}")
    private String routingKeyName;


    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding binding(final Queue queue, final DirectExchange directExchange) {
        return BindingBuilder.bind(queue)
                .to(directExchange)
                .with(routingKeyName);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory factory) {
        final RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
