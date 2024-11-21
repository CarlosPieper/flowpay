package com.flowpay.core.usecases.attendants;

import com.flowpay.core.models.attendants.addTicket.AddTicketToAttendantRequest;

public interface AddTicketToAttendantUseCase {
    void execute(AddTicketToAttendantRequest request);
}
