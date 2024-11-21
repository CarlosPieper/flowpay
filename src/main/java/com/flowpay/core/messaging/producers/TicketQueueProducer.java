package com.flowpay.core.messaging.producers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.flowpay.core.domain.Ticket;

public interface TicketQueueProducer {
    void queueTicket(Ticket ticket) throws JsonProcessingException;
}
