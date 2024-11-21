package com.flowpay.core.messaging.consumers;

import com.flowpay.core.domain.Attendant;

public interface QueuedTicketsConsumer {
    void addQueuedTicketToAttendant(Attendant attendant);
}
