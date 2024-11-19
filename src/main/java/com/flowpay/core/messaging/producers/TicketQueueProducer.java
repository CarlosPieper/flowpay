package com.flowpay.core.messaging.producers;


import com.flowpay.core.domain.Ticket;

public interface TicketQueueProducer {
    void queueTicket(Ticket ticket);
}
