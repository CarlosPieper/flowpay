package com.flowpay.core.messaging.consumers;

import java.util.UUID;

public interface QueuedTicketsConsumer {
    void assignQueuedTicketToSupport(UUID supportId);
    void addTicketsToNewSupport(UUID supportId);
}
