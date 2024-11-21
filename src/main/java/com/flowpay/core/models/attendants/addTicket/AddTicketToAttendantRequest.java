package com.flowpay.core.models.attendants.addTicket;

import java.util.UUID;

public record AddTicketToAttendantRequest(UUID ticketId, UUID attendantId) {
}
