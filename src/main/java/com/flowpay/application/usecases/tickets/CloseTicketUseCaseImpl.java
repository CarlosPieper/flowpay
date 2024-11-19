package com.flowpay.application.usecases.tickets;

import com.flowpay.core.models.tickets.close.CloseTicketRequest;
import com.flowpay.core.models.tickets.close.CloseTicketResponse;
import com.flowpay.core.repositories.TicketsRepository;
import com.flowpay.core.usecases.tickets.CloseTicketUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CloseTicketUseCaseImpl implements CloseTicketUseCase {

    @Autowired
    private TicketsRepository ticketsRepository;

    @Override
    public CloseTicketResponse execute(CloseTicketRequest request) {
        var ticket = ticketsRepository.findById(request.ticketId());
        ticket.close();
        ticketsRepository.update(ticket);
        return new CloseTicketResponse();
    }
}
