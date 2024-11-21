package com.flowpay.core.domain;

import com.flowpay.core.domain.enumerators.AreaEnum;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class Attendant extends User {

    public Attendant() {
    }

    private Attendant(String email, String name, UUID id, AreaEnum area) {
        this.setEmail(email);
        this.setName(name);
        this.setId(id);
        this.setArea(area);
    }

    public static Attendant create(String email, String name, AreaEnum area) {
        return new Attendant(email, name, UUID.randomUUID(), area);
    }

    private final ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    private AreaEnum area;

    public AreaEnum getArea() {
        return area;
    }

    public void setArea(AreaEnum area) {
        this.area = area;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
    }
}
