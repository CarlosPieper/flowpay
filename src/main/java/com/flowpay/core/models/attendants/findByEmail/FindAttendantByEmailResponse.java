package com.flowpay.core.models.attendants.findByEmail;

import com.flowpay.core.domain.enumerators.AreaEnum;
import com.flowpay.core.models._shared.TicketDTO;

import java.util.List;
import java.util.UUID;

public record FindAttendantByEmailResponse(UUID id, String email, String name, AreaEnum area, List<TicketDTO> tickets) {
}
