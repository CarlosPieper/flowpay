package com.flowpay.infrastructure.messaging.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flowpay.core.messaging._config.AttendantsMessagingConfig;
import com.flowpay.core.messaging.consumers.QueuedAttendantsConsumer;
import com.flowpay.core.models.attendants.addTicket.AddTicketToAttendantRequest;
import com.flowpay.core.repositories.AttendantsRepository;
import com.flowpay.core.usecases.attendants.AddTicketToAttendantUseCase;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class QueuedAttendantsConsumerImpl implements QueuedAttendantsConsumer {

    @Autowired
    private AttendantsRepository attendantsRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private AddTicketToAttendantUseCase addTicketToAttendantUseCase;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(AttendantsMessagingConfig.QUEUE_NAME),
                    exchange = @Exchange(AttendantsMessagingConfig.EXCHANGE_NAME),
                    key = AttendantsMessagingConfig.ROUTING_KEY
            )
    )

    @Override
    public void addTicketsToAttendant(final Message message, final UUID attendantId) {
        var attendant = attendantsRepository.findById(attendantId);
        var queue = "TICKETS_" + attendant.getArea().toString() + "_QUEUE";
        for (int i = 0; i < 3; i++) {
            var content = (String) amqpTemplate.receiveAndConvert(queue);

            if (content == null || content.isEmpty()) {
                return;
            }

            var ticketId = UUID.fromString(content);
            addTicketToAttendantUseCase.execute(new AddTicketToAttendantRequest(ticketId, attendantId));
        }
    }
}
