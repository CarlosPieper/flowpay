package com.flowpay.infrastructure.repositories.inMemory;

import com.flowpay.core.domain.Ticket;
import com.flowpay.core.repositories.TicketsRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public class InMemoryTicketsRepository implements TicketsRepository {

    private final ArrayList<Ticket> tickets = new ArrayList<>();

    @Override
    public void create(Ticket ticket) {
        this.tickets.add(ticket);
    }

    @Override
    public Ticket findById(UUID ticketId) {
        return this.tickets.stream().filter(ticket -> ticket.getId().equals(ticketId)).findFirst().orElse(null);
    }
}
