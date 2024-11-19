package com.flowpay.core.models._shared;

import com.flowpay.core.domain.enumerators.AreaEnum;

import java.util.UUID;

public record TicketDTO(UUID id, String title, String description, AreaEnum area) { }
