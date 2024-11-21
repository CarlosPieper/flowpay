package com.flowpay.core.repositories;

import com.flowpay.core.domain.Ticket;

import java.util.UUID;

public interface TicketsRepository {
    void create(Ticket ticket);

    Ticket findById(UUID ticketId);
}
