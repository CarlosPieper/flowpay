package com.flowpay.core.messaging.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flowpay.core.domain.Attendant;

public interface AttendantQueueProducer {
    void queueAttendant(Attendant attendant) throws JsonProcessingException;
}
