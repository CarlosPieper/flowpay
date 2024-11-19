package com.flowpay.core.models.tickets.create;

import com.flowpay.core.domain.enumerators.AreaEnum;

import java.util.UUID;

public record CreateTicketResponse(UUID id, String title, String description, AreaEnum area) {
}