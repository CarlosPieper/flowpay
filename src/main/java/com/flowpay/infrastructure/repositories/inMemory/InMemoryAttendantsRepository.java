package com.flowpay.infrastructure.repositories.inMemory;

import com.flowpay.core.domain.Attendant;
import com.flowpay.core.domain.Ticket;
import com.flowpay.core.domain.enumerators.AreaEnum;
import com.flowpay.core.repositories.AttendantsRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public class InMemoryAttendantsRepository implements AttendantsRepository {

    private final ArrayList<Attendant> attendants = new ArrayList<>();

    @Override
    public void create(Attendant attendant) {
        this.attendants.add(attendant);
    }

    @Override
    public Attendant findById(UUID attendantId) {
        return this.attendants.stream()
                .filter(attendant -> attendant.getId().equals(attendantId)).findFirst().orElse(null);
    }

    @Override
    public Attendant findByEmail(String email) {
        return this.attendants.stream()
                .filter(attendant -> attendant.getEmail().equals(email)).findFirst().orElse(null);
    }

    @Override
    public Attendant findAvailableAttendantInArea(AreaEnum area) {
        return this.attendants.stream()
                .filter(attendant -> attendant.getTickets().stream().filter(Ticket::isOpen).toList().size() < 3)
                .findFirst().orElse(null);
    }
}
