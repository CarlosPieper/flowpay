package com.flowpay.application.usecases.tickets;

import com.flowpay.core.exceptions.EmptyTextException;
import com.flowpay.core.exceptions.RecordNotFoundException;
import com.flowpay.core.messaging.consumers.QueuedTicketsConsumer;
import com.flowpay.core.models.tickets.close.CloseTicketRequest;
import com.flowpay.core.models.tickets.close.CloseTicketResponse;
import com.flowpay.core.repositories.AttendantsRepository;
import com.flowpay.core.repositories.TicketsRepository;
import com.flowpay.core.usecases.tickets.CloseTicketUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CloseTicketUseCaseImpl implements CloseTicketUseCase {

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private AttendantsRepository attendantsRepository;

    @Autowired
    private QueuedTicketsConsumer queuedTicketsConsumer;

    @Override
    public CloseTicketResponse execute(CloseTicketRequest request) {
        if (request.ticketId() == null) {
            throw new EmptyTextException("ID");
        }
        var ticket = ticketsRepository.findById(request.ticketId());
        if (ticket == null) {
            throw new RecordNotFoundException();
        }
        var attendant = attendantsRepository.findAttendantWithTicket(request.ticketId());
        ticket.close();
        attendant.removeTicket(ticket);
        queuedTicketsConsumer.addQueuedTicketToAttendant(attendant);
        return new CloseTicketResponse();
    }
}
