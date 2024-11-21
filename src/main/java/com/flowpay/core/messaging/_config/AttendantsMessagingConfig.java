package com.flowpay.core.messaging._config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AttendantsMessagingConfig {
    public static final String QUEUE_NAME = "ATTENDANTS_QUEUE";
    public static final String EXCHANGE_NAME = "ATTENDANTS_EXCHANGE";
    public static final String ROUTING_KEY = "CREATE_ATTENDANT";

    @Bean
    DirectExchange attendantsExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue attendantsQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }

    @Bean
    Binding attendantsBinding() {
        return BindingBuilder.bind(attendantsQueue()).to(attendantsExchange()).with(ROUTING_KEY);
    }
}
