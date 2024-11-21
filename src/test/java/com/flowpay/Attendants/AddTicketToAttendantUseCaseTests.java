package com.flowpay.Attendants;

import com.flowpay.application.usecases.attendants.AddTicketToAttendantUseCaseImpl;
import com.flowpay.core.domain.Attendant;
import com.flowpay.core.domain.Ticket;
import com.flowpay.core.models.attendants.addTicket.AddTicketToAttendantRequest;
import com.flowpay.core.repositories.AttendantsRepository;
import com.flowpay.core.repositories.TicketsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class AddTicketToAttendantUseCaseTests {
    @Mock
    private AttendantsRepository attendantsRepository;

    @Mock
    private TicketsRepository ticketsRepository;

    @InjectMocks
    private AddTicketToAttendantUseCaseImpl addTicketToAttendantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldAddTicketToAttendant() {
        // Arrange
        UUID attendantId = UUID.randomUUID();
        UUID ticketId = UUID.randomUUID();

        AddTicketToAttendantRequest request = new AddTicketToAttendantRequest(ticketId, attendantId);

        Attendant attendant = mock(Attendant.class);
        Ticket ticket = mock(Ticket.class);

        when(attendantsRepository.findById(attendantId)).thenReturn(attendant);
        when(ticketsRepository.findById(ticketId)).thenReturn(ticket);

        // Act
        addTicketToAttendantUseCase.execute(request);

        // Assert
        verify(attendantsRepository, times(1)).findById(attendantId);
        verify(ticketsRepository, times(1)).findById(ticketId);
        verify(attendant, times(1)).addTicket(ticket);
    }
}
