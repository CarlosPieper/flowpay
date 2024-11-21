package com.flowpay.Tickets;

import com.flowpay.application.usecases.tickets.CloseTicketUseCaseImpl;
import com.flowpay.core.domain.Attendant;
import com.flowpay.core.domain.Ticket;
import com.flowpay.core.exceptions.EmptyTextException;
import com.flowpay.core.exceptions.RecordNotFoundException;
import com.flowpay.core.messaging.consumers.QueuedTicketsConsumer;
import com.flowpay.core.models.tickets.close.CloseTicketRequest;
import com.flowpay.core.repositories.AttendantsRepository;
import com.flowpay.core.repositories.TicketsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CloseTicketUseCaseTests {
    @Mock
    private TicketsRepository ticketsRepository;

    @Mock
    private AttendantsRepository attendantsRepository;

    @Mock
    private QueuedTicketsConsumer queuedTicketsConsumer;

    @InjectMocks
    private CloseTicketUseCaseImpl closeTicketUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldCloseTicketAndRemoveFromAttendant() {
        // Arrange
        UUID ticketId = UUID.randomUUID();
        CloseTicketRequest request = new CloseTicketRequest(ticketId);

        Ticket ticket = mock(Ticket.class);
        Attendant attendant = mock(Attendant.class);

        when(ticketsRepository.findById(ticketId)).thenReturn(ticket);
        when(attendantsRepository.findAttendantWithTicket(ticketId)).thenReturn(attendant);

        // Act
        closeTicketUseCase.execute(request);

        // Assert
        verify(ticketsRepository, times(1)).findById(ticketId);
        verify(attendantsRepository, times(1)).findAttendantWithTicket(ticketId);
        verify(ticket, times(1)).close();
        verify(attendant, times(1)).removeTicket(ticket);
        verify(queuedTicketsConsumer, times(1)).addQueuedTicketToAttendant(attendant);
    }

    @Test
    void execute_shouldThrowExceptionWhenTicketIdIsnull() {
        // Arrange
        CloseTicketRequest request = new CloseTicketRequest(null);

        // Act & Assert
        EmptyTextException exception = assertThrows(EmptyTextException.class, () -> closeTicketUseCase.execute(request));
        assertEquals("ID é obrigatório.", exception.getMessage());
    }

    @Test
    void execute_shouldThrowExceptionWhenTicketDoesNotExist() {
        // Arrange
        var id = UUID.randomUUID();
        CloseTicketRequest request = new CloseTicketRequest(id);
        when(ticketsRepository.findById(id)).thenReturn(null);
        // Act & Assert
        RecordNotFoundException exception = assertThrows(RecordNotFoundException.class, () -> closeTicketUseCase.execute(request));
        assertEquals("Registro não encontrado.", exception.getMessage());
    }
}
