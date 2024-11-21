package com.flowpay.infrastructure.messaging.consumers;

import com.flowpay.core.domain.Attendant;
import com.flowpay.core.messaging.consumers.QueuedTicketsConsumer;
import com.flowpay.core.models.attendants.addTicket.AddTicketToAttendantRequest;
import com.flowpay.core.usecases.attendants.AddTicketToAttendantUseCase;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueuedTicketsConsumerImpl implements QueuedTicketsConsumer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private AddTicketToAttendantUseCase addTicketToAttendantUseCase;

    @Override
    public void addQueuedTicketToAttendant(Attendant attendant) {
        var queue = "TICKETS_" + attendant.getArea().toString() + "_QUEUE";

        var content = (String) amqpTemplate.receiveAndConvert(queue);

        if (content == null || content.isEmpty()) {
            return;
        }

        var ticketId = UUID.fromString(content);
        addTicketToAttendantUseCase.execute(new AddTicketToAttendantRequest(ticketId, attendant.getId()));
    }
}
