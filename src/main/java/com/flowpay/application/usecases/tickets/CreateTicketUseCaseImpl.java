package com.flowpay.application.usecases.tickets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flowpay.core.domain.Ticket;
import com.flowpay.core.exceptions.EmptyTextException;
import com.flowpay.core.messaging.producers.TicketQueueProducer;
import com.flowpay.core.models.tickets.create.CreateTicketRequest;
import com.flowpay.core.models.tickets.create.CreateTicketResponse;
import com.flowpay.core.repositories.AttendantsRepository;
import com.flowpay.core.repositories.TicketsRepository;
import com.flowpay.core.usecases.tickets.CreateTicketUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTicketUseCaseImpl implements CreateTicketUseCase {

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    TicketQueueProducer ticketQueueProducer;

    @Autowired
    private AttendantsRepository attendantsRepository;

    public CreateTicketResponse execute(CreateTicketRequest request) throws JsonProcessingException {
        if (request.title() == null || request.title().isEmpty()) {
            throw new EmptyTextException("Título");
        }
        if (request.description() == null || request.description().isEmpty()) {
            throw new EmptyTextException("Descrição");
        }
        if (request.area() == null) {
            throw new EmptyTextException("Área");
        }

        var ticket = Ticket.create(request.title(), request.description(), request.area());
        ticketsRepository.create(ticket);

        var attendant = attendantsRepository.findAvailableAttendantInArea(ticket.getArea());
        if (attendant != null) {
            attendant.addTicket(ticket);
        }
        else {
            ticketQueueProducer.queueTicket(ticket);
        }

        return new CreateTicketResponse(ticket.getId(), ticket.getTitle(), ticket.getDescription(), ticket.getArea());
    }
}
