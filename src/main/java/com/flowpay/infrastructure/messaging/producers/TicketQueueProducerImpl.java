package com.flowpay.infrastructure.messaging.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flowpay.core.domain.Ticket;
import com.flowpay.core.messaging._config.TicketsMessagingConfig;
import com.flowpay.core.messaging.producers.TicketQueueProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketQueueProducerImpl implements TicketQueueProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void queueTicket(Ticket ticket) throws JsonProcessingException {
        var content = ticket.getId().toString();
        amqpTemplate.convertAndSend(TicketsMessagingConfig.EXCHANGE_NAME, "CREATE_" + ticket.getArea().toString() + "_TICKET", content);
    }

}
