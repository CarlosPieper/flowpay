package com.flowpay.infrastructure.messaging.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flowpay.core.domain.Attendant;
import com.flowpay.core.messaging._config.AttendantsMessagingConfig;
import com.flowpay.core.messaging.producers.AttendantQueueProducer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendantQueueProducerImpl implements AttendantQueueProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void queueAttendant(Attendant attendant) throws JsonProcessingException {
        var content = attendant.getId().toString();
        amqpTemplate.convertAndSend(AttendantsMessagingConfig.EXCHANGE_NAME, AttendantsMessagingConfig.ROUTING_KEY, content);
    }
}
