package com.flowpay.application.usecases.attendants;

import com.flowpay.core.models.attendants.addTicket.AddTicketToAttendantRequest;
import com.flowpay.core.repositories.AttendantsRepository;
import com.flowpay.core.repositories.TicketsRepository;
import com.flowpay.core.usecases.attendants.AddTicketToAttendantUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddTicketToAttendantUseCaseImpl implements AddTicketToAttendantUseCase {

    @Autowired
    private AttendantsRepository attendantsRepository;

    @Autowired
    private TicketsRepository ticketsRepository;

    @Override
    public void execute(AddTicketToAttendantRequest request) {
        var attendant = attendantsRepository.findById(request.attendantId());
        var ticket = ticketsRepository.findById(request.ticketId());
        attendant.addTicket(ticket);
    }
}
