package com.flowpay.core.usecases.tickets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flowpay.core.models.tickets.create.CreateTicketRequest;
import com.flowpay.core.models.tickets.create.CreateTicketResponse;

public interface CreateTicketUseCase {
    CreateTicketResponse execute(CreateTicketRequest request) throws JsonProcessingException;
}
