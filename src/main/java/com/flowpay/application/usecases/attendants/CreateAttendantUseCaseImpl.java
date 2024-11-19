package com.flowpay.application.usecases.attendants;

import com.flowpay.core.domain.Attendant;
import com.flowpay.core.exceptions.EmailAlreadyRegisteredException;
import com.flowpay.core.exceptions.EmptyTextException;
import com.flowpay.core.models.attendants.create.CreateAttendantRequest;
import com.flowpay.core.models.attendants.create.CreateAttendantResponse;
import com.flowpay.core.repositories.AttendantsRepository;
import com.flowpay.core.usecases.attendants.CreateAttendantUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAttendantUseCaseImpl implements CreateAttendantUseCase {

    @Autowired
    AttendantsRepository attendantsRepository;

    @Override
    public CreateAttendantResponse execute(CreateAttendantRequest request) {
        if (request.name() == null || request.name().isEmpty()) {
            throw new EmptyTextException("Nome");
        }
        if (request.email() == null ||request.email().isEmpty()) {
            throw new EmptyTextException("Email");
        }
        var entityWithSameEmail = attendantsRepository.findByEmail(request.email());
        if (entityWithSameEmail != null) {
            throw new EmailAlreadyRegisteredException();
        }

        Attendant entity = Attendant.Create(request.email(), request.name(), request.area());
        attendantsRepository.create(entity);
        return new CreateAttendantResponse(entity.getId(), entity.getEmail(), entity.getName(), entity.getArea(), null);
    }
}