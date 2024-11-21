package com.flowpay.core.usecases.attendants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flowpay.core.models.attendants.create.CreateAttendantRequest;
import com.flowpay.core.models.attendants.create.CreateAttendantResponse;

public interface CreateAttendantUseCase {
    CreateAttendantResponse execute(CreateAttendantRequest request) throws JsonProcessingException;
}
