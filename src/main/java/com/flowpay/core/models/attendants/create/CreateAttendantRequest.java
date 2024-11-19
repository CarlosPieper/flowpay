package com.flowpay.core.models.attendants.create;

import com.flowpay.core.domain.enumerators.AreaEnum;

public record CreateAttendantRequest(String email, String name, AreaEnum area) {
}
