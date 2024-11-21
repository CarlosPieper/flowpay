package com.flowpay.core.messaging._config;

import com.flowpay.core.domain.enumerators.AreaEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TicketsMessagingConfig {
    public static final String EXCHANGE_NAME = "TICKETS_EXCHANGE";

    public static final String QUEUE_CARDS = "TICKETS_CARDS_QUEUE";
    public static final String QUEUE_LOANS = "TICKETS_LOANS_QUEUE";
    public static final String QUEUE_OTHERS = "TICKETS_OTHERS_QUEUE";

    public static final String ROUTING_KEY_CARDS = "CREATE_CARDS_TICKET";
    public static final String ROUTING_KEY_LOANS = "CREATE_LOANS_TICKET";
    public static final String ROUTING_KEY_OTHERS = "CREATE_OTHERS_TICKET";

    @Bean
    DirectExchange ticketsExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue cardsQueue() {
        return QueueBuilder.durable(QUEUE_CARDS).build();
    }

    @Bean
    Queue loansQueue() {
        return QueueBuilder.durable(QUEUE_LOANS).build();
    }

    @Bean
    Queue othersQueue() {
        return QueueBuilder.durable(QUEUE_OTHERS).build();
    }

    @Bean
    Binding cardsBinding() {
        return BindingBuilder.bind(cardsQueue()).to(ticketsExchange()).with(ROUTING_KEY_CARDS);
    }

    @Bean
    Binding loansBinding() {
        return BindingBuilder.bind(loansQueue()).to(ticketsExchange()).with(ROUTING_KEY_LOANS);
    }

    @Bean
    Binding othersBinding() {
        return BindingBuilder.bind(othersQueue()).to(ticketsExchange()).with(ROUTING_KEY_OTHERS);
    }
}
