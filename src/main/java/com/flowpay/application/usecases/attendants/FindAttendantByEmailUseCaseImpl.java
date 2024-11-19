package com.flowpay.application.usecases.attendants;

import com.flowpay.core.exceptions.EmptyTextException;
import com.flowpay.core.exceptions.RecordNotFoundException;
import com.flowpay.core.models.attendants.findByEmail.FindAttendantByEmailRequest;
import com.flowpay.core.models.attendants.findByEmail.FindAttendantByEmailResponse;
import com.flowpay.core.repositories.AttendantsRepository;
import com.flowpay.core.usecases.attendants.FindAttendantByEmailUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindAttendantByEmailUseCaseImpl implements FindAttendantByEmailUseCase {

    @Autowired
    AttendantsRepository attendantsRepository;

    @Override
    public FindAttendantByEmailResponse execute(FindAttendantByEmailRequest request) {
        if (request.email() == null || request.email().isEmpty()) {
            throw new EmptyTextException("Email");
        }

        var entity = attendantsRepository.findByEmail(request.email());
        if (entity == null) {
            return null;
        }

        return new FindAttendantByEmailResponse(entity.getId(), entity.getEmail(), entity.getName(), entity.getArea(), null);
    }
}
