package com.flowpay.core.messaging.consumers;

import org.springframework.amqp.core.Message;

import java.util.UUID;

public interface QueuedAttendantsConsumer {
    void addTicketsToAttendant(final Message message, final UUID attendantId);
}
