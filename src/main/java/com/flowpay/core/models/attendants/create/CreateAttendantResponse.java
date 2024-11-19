package com.flowpay.core.models.attendants.create;

import com.flowpay.core.domain.enumerators.AreaEnum;
import com.flowpay.core.models._shared.TicketDTO;

import java.util.ArrayList;
import java.util.UUID;

public record CreateAttendantResponse (UUID id, String email, String name, AreaEnum area, ArrayList<TicketDTO> tickets) {
}
