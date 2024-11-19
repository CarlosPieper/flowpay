package com.flowpay.api.controllers;

import com.flowpay.core.exceptions.EmptyTextException;
import com.flowpay.core.models.tickets.close.CloseTicketRequest;
import com.flowpay.core.models.tickets.create.CreateTicketRequest;
import com.flowpay.core.usecases.tickets.CloseTicketUseCase;
import com.flowpay.core.usecases.tickets.CreateTicketUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tickets")
public class TicketsController {

    @Autowired
    CreateTicketUseCase createTicketUseCase;

    @Autowired
    CloseTicketUseCase closeTicketUseCase;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateTicketRequest request) {
        try {
            var response = createTicketUseCase.execute(request);
            return ResponseEntity.ok(response);
        } catch (EmptyTextException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/close/{ticketId}")
    public ResponseEntity<?> close(@PathVariable UUID ticketId) {
        try {
            var request = new CloseTicketRequest(ticketId);
            var response = closeTicketUseCase.execute(request);
            return ResponseEntity.ok(response);
        } catch (EmptyTextException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
