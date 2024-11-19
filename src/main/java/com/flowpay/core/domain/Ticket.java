package com.flowpay.core.domain;

import com.flowpay.core.domain.enumerators.AreaEnum;

import java.util.UUID;

public class Ticket {

    public Ticket() {
    }

    public Ticket(String title, String description, boolean isOpen, UUID id, AreaEnum area) {
        this.setTitle(title);
        this.setDescription(description);
        this.setOpen(isOpen);
        this.setId(id);
        this.setArea(area);
    }

    public static Ticket create(String title, String description, AreaEnum area) {
        return new Ticket(title, description, true, UUID.randomUUID(), area);
    }

    public void close() {
        this.setOpen(false);
    }

    private UUID id;
    private String title;
    private String description;
    private boolean isOpen;
    private AreaEnum area;

    public AreaEnum getArea() {
        return area;
    }

    public void setArea(AreaEnum area) {
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
