package com.flowpay.api.controllers;

import com.flowpay.core.exceptions.EmailAlreadyRegisteredException;
import com.flowpay.core.exceptions.EmptyTextException;
import com.flowpay.core.exceptions.RecordNotFoundException;
import com.flowpay.core.models.attendants.create.CreateAttendantRequest;
import com.flowpay.core.models.attendants.findByEmail.FindAttendantByEmailRequest;
import com.flowpay.core.usecases.attendants.CreateAttendantUseCase;
import com.flowpay.core.usecases.attendants.FindAttendantByEmailUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attendants")
public class AttendantsController {

    @Autowired
    CreateAttendantUseCase createAttendantUseCase;

    @Autowired
    FindAttendantByEmailUseCase findAttendantByEmailUseCase;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateAttendantRequest request) {
        try {
            var response = createAttendantUseCase.execute(request);
            return ResponseEntity.ok(response);
        } catch (EmptyTextException | EmailAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/find-by-email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        try {
            var request = new FindAttendantByEmailRequest(email);
            var response = findAttendantByEmailUseCase.execute(request);

            return ResponseEntity.ok(response);

        } catch (EmptyTextException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
