package com.flowpay.Attendants;

import com.flowpay.application.usecases.attendants.FindAttendantByEmailUseCaseImpl;
import com.flowpay.core.domain.Attendant;
import com.flowpay.core.domain.Ticket;
import com.flowpay.core.domain.enumerators.AreaEnum;
import com.flowpay.core.exceptions.EmptyTextException;
import com.flowpay.core.models.attendants.findByEmail.FindAttendantByEmailRequest;
import com.flowpay.core.models.attendants.findByEmail.FindAttendantByEmailResponse;
import com.flowpay.core.repositories.AttendantsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FindAttendantByEmailUseCaseTests {
    @Mock
    private AttendantsRepository attendantsRepository;

    @InjectMocks
    private FindAttendantByEmailUseCaseImpl findAttendantByEmailUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldReturnAttendantWhenFound() {
        // Arrange
        String email = "john.doe@example.com";
        FindAttendantByEmailRequest request = new FindAttendantByEmailRequest(email);

        Ticket ticket = Ticket.create("Title1", "Description1", AreaEnum.CARDS);

        Attendant attendant = Attendant.create("john doe", email, AreaEnum.CARDS);
        attendant.addTicket(ticket);

        when(attendantsRepository.findByEmail(email)).thenReturn(attendant);

        // Act
        FindAttendantByEmailResponse response = findAttendantByEmailUseCase.execute(request);

        // Assert
        verify(attendantsRepository, times(1)).findByEmail(email);
        assertNotNull(response);
        assertEquals(attendant.getId(), response.id());
        assertEquals(attendant.getEmail(), response.email());
        assertEquals(attendant.getName(), response.name());
        assertEquals(attendant.getArea(), response.area());
        assertEquals(attendant.getTickets().size(), response.tickets().size());
        assertEquals(ticket.getId(), response.tickets().get(0).id());
        assertEquals(ticket.getTitle(), response.tickets().get(0).title());
        assertEquals(ticket.getDescription(), response.tickets().get(0).description());
        assertEquals(ticket.getArea(), response.tickets().get(0).area());
    }

    @Test
    void execute_shouldReturnNullWhenAttendantNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        FindAttendantByEmailRequest request = new FindAttendantByEmailRequest(email);

        when(attendantsRepository.findByEmail(email)).thenReturn(null);

        // Act
        FindAttendantByEmailResponse response = findAttendantByEmailUseCase.execute(request);

        // Assert
        verify(attendantsRepository, times(1)).findByEmail(email);
        assertNull(response);
    }

    @Test
    void execute_shouldThrowExceptionWhenEmailIsEmpty() {
        // Arrange
        FindAttendantByEmailRequest request = new FindAttendantByEmailRequest("");

        // Act & Assert
        EmptyTextException exception = assertThrows(EmptyTextException.class,
                () -> findAttendantByEmailUseCase.execute(request));
        assertEquals("Email é obrigatório.", exception.getMessage());
        verifyNoInteractions(attendantsRepository);
    }

    @Test
    void execute_shouldThrowExceptionWhenEmailIsNull() {
        // Arrange
        FindAttendantByEmailRequest request = new FindAttendantByEmailRequest(null);

        // Act & Assert
        EmptyTextException exception = assertThrows(EmptyTextException.class,
                () -> findAttendantByEmailUseCase.execute(request));
        assertEquals("Email é obrigatório.", exception.getMessage());
        verifyNoInteractions(attendantsRepository);
    }
}
