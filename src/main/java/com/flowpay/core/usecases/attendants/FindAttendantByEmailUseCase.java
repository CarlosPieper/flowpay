package com.flowpay.core.usecases.attendants;

import com.flowpay.core.models.attendants.findByEmail.FindAttendantByEmailRequest;
import com.flowpay.core.models.attendants.findByEmail.FindAttendantByEmailResponse;

public interface FindAttendantByEmailUseCase {
    FindAttendantByEmailResponse execute(FindAttendantByEmailRequest request);
}
