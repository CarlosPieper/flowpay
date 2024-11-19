package com.flowpay.core.usecases.tickets;

import com.flowpay.core.models.tickets.close.CloseTicketRequest;
import com.flowpay.core.models.tickets.close.CloseTicketResponse;

public interface CloseTicketUseCase {
    CloseTicketResponse execute(CloseTicketRequest request);
}
