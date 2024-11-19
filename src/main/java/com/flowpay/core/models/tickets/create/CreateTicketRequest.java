package com.flowpay.core.models.tickets.create;

import com.flowpay.core.domain.enumerators.AreaEnum;

public record CreateTicketRequest(String title, String description, AreaEnum area) {
}