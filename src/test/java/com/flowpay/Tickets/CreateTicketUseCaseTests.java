package com.flowpay.Tickets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flowpay.application.usecases.tickets.CreateTicketUseCaseImpl;
import com.flowpay.core.domain.Attendant;
import com.flowpay.core.domain.Ticket;
import com.flowpay.core.domain.enumerators.AreaEnum;
import com.flowpay.core.exceptions.EmptyTextException;
import com.flowpay.core.messaging.producers.TicketQueueProducer;
import com.flowpay.core.models.tickets.create.CreateTicketRequest;
import com.flowpay.core.models.tickets.create.CreateTicketResponse;
import com.flowpay.core.repositories.AttendantsRepository;
import com.flowpay.core.repositories.TicketsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CreateTicketUseCaseTests {

    @Mock
    private TicketsRepository ticketsRepository;

    @Mock
    private TicketQueueProducer ticketQueueProducer;

    @Mock
    private AttendantsRepository attendantsRepository;

    @InjectMocks
    private CreateTicketUseCaseImpl createTicketUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldThrowExceptionWhenTitleIsEmpty() {
        // Arrange
        CreateTicketRequest request = new CreateTicketRequest("", "Valid description", AreaEnum.CARDS);

        // Act & Assert
        EmptyTextException exception = assertThrows(EmptyTextException.class, () -> createTicketUseCase.execute(request));
        assertEquals("Título é obrigatório.", exception.getMessage());
    }

    @Test
    void execute_shouldThrowExceptionWhenDescriptionIsEmpty() {
        // Arrange
        CreateTicketRequest request = new CreateTicketRequest("Valid title", "", AreaEnum.CARDS);

        // Act & Assert
        EmptyTextException exception = assertThrows(EmptyTextException.class, () -> createTicketUseCase.execute(request));
        assertEquals("Descrição é obrigatório.", exception.getMessage());
    }

    @Test
    void execute_shouldThrowExceptionWhenAreaIsNull() {
        // Arrange
        CreateTicketRequest request = new CreateTicketRequest("Valid title", "Valid description", null);

        // Act & Assert
        EmptyTextException exception = assertThrows(EmptyTextException.class, () -> createTicketUseCase.execute(request));
        assertEquals("Área é obrigatório.", exception.getMessage());
    }

    @Test
    void execute_shouldCreateTicketAndAssignToAttendant() throws JsonProcessingException {
        // Arrange
        CreateTicketRequest request = new CreateTicketRequest("Valid title", "Valid description", AreaEnum.CARDS);
        Ticket ticket = Ticket.create("Valid title", "Valid description", AreaEnum.CARDS);
        Attendant attendant = new Attendant();

        when(attendantsRepository.findAvailableAttendantInArea(AreaEnum.CARDS)).thenReturn(attendant);

        // Act
        CreateTicketResponse response = createTicketUseCase.execute(request);

        // Assert
        verify(ticketsRepository, times(1)).create(argThat(t ->
                ticket.getTitle().equals(t.getTitle()) &&
                        ticket.getDescription().equals(t.getDescription()) &&
                        ticket.getArea().equals(t.getArea())
        ));
        verify(ticketQueueProducer, never()).queueTicket(ticket);
        assertEquals(ticket.getTitle(), response.title());
        assertEquals(ticket.getDescription(), response.description());
        assertEquals(ticket.getArea(), response.area());
        assertTrue(attendant.getTickets().stream().anyMatch(t ->
                t.isOpen() &&
                        t.getTitle().equals(ticket.getTitle()) &&
                        t.getDescription().equals(ticket.getDescription()) &&
                        t.getArea().equals(ticket.getArea())));
    }

    @Test
    void execute_shouldQueueTicketWhenNoAttendantIsAvailable() throws JsonProcessingException {
        // Arrange
        CreateTicketRequest request = new CreateTicketRequest("Valid title", "Valid description", AreaEnum.CARDS);
        Ticket ticket = Ticket.create("Valid title", "Valid description", AreaEnum.CARDS);

        when(attendantsRepository.findAvailableAttendantInArea(AreaEnum.CARDS)).thenReturn(null);

        // Act
        CreateTicketResponse response = createTicketUseCase.execute(request);

        // Assert
        verify(ticketsRepository, times(1)).create(argThat(t ->
                ticket.getTitle().equals(t.getTitle()) &&
                        ticket.getDescription().equals(t.getDescription()) &&
                        ticket.getArea().equals(t.getArea())
        ));
        verify(ticketQueueProducer, times(1)).queueTicket(argThat(t ->
                ticket.getTitle().equals(t.getTitle()) &&
                        ticket.getDescription().equals(t.getDescription()) &&
                        ticket.getArea().equals(t.getArea())
        ));
        assertEquals(ticket.getTitle(), response.title());
        assertEquals(ticket.getDescription(), response.description());
        assertEquals(ticket.getArea(), response.area());
    }
}
