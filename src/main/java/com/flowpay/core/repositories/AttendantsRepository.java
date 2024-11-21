package com.flowpay.core.repositories;

import com.flowpay.core.domain.Attendant;
import com.flowpay.core.domain.enumerators.AreaEnum;

import java.util.UUID;

public interface AttendantsRepository {
    void create(Attendant attendant);

    Attendant findById(UUID attendantId);

    Attendant findByEmail(String email);

    Attendant findAvailableAttendantInArea(AreaEnum area);
    Attendant findAttendantWithTicket(UUID ticketId);
}
