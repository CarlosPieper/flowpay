package com.flowpay.Attendants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.flowpay.application.usecases.attendants.CreateAttendantUseCaseImpl;
import com.flowpay.core.domain.Attendant;
import com.flowpay.core.domain.enumerators.AreaEnum;
import com.flowpay.core.exceptions.EmailAlreadyRegisteredException;
import com.flowpay.core.exceptions.EmptyTextException;
import com.flowpay.core.messaging.producers.AttendantQueueProducer;
import com.flowpay.core.models.attendants.create.CreateAttendantRequest;
import com.flowpay.core.models.attendants.create.CreateAttendantResponse;
import com.flowpay.core.repositories.AttendantsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateAttendantUseCaseTests {

    @Mock
    private AttendantsRepository attendantsRepository;

    @Mock
    private AttendantQueueProducer attendantQueueProducer;

    @InjectMocks
    private CreateAttendantUseCaseImpl createAttendantUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_shouldCreateAttendantAndQueue() throws JsonProcessingException {
        // Arrange
        CreateAttendantRequest request = new CreateAttendantRequest("john.doe@example.com", "John Doe", AreaEnum.CARDS);
        when(attendantsRepository.findByEmail(request.email())).thenReturn(null);

        // Act
        CreateAttendantResponse response = createAttendantUseCase.execute(request);

        // Assert
        verify(attendantsRepository, times(1)).create(argThat(attendant ->
                request.email().equals(attendant.getEmail()) &&
                        request.name().equals(attendant.getName()) &&
                        request.area().equals(attendant.getArea())
        ));
        verify(attendantQueueProducer, times(1)).queueAttendant(argThat(attendant ->
                request.email().equals(attendant.getEmail()) &&
                        request.name().equals(attendant.getName()) &&
                        request.area().equals(attendant.getArea())
        ));
        assertNotNull(response);
        assertEquals(request.email(), response.email());
        assertEquals(request.name(), response.name());
        assertEquals(request.area(), response.area());
    }

    @Test
    void execute_shouldThrowExceptionWhenNameIsEmpty() {
        // Arrange
        CreateAttendantRequest request = new CreateAttendantRequest("john.doe@example.com", "", AreaEnum.CARDS);

        // Act & Assert
        EmptyTextException exception = assertThrows(EmptyTextException.class, () -> createAttendantUseCase.execute(request));
        assertEquals("Nome é obrigatório.", exception.getMessage());
        verifyNoInteractions(attendantsRepository);
        verifyNoInteractions(attendantQueueProducer);
    }

    @Test
    void execute_shouldThrowExceptionWhenEmailIsEmpty() {
        // Arrange
        CreateAttendantRequest request = new CreateAttendantRequest("", "John Doe", AreaEnum.CARDS);

        // Act & Assert
        EmptyTextException exception = assertThrows(EmptyTextException.class, () -> createAttendantUseCase.execute(request));
        assertEquals("Email é obrigatório.", exception.getMessage());
        verifyNoInteractions(attendantsRepository);
        verifyNoInteractions(attendantQueueProducer);
    }

    @Test
    void execute_shouldThrowExceptionWhenEmailAlreadyRegistered() {
        // Arrange
        CreateAttendantRequest request = new CreateAttendantRequest("john.doe@example.com", "John Doe", AreaEnum.CARDS);
        when(attendantsRepository.findByEmail(request.email())).thenReturn(mock(Attendant.class));

        // Act & Assert
        assertThrows(EmailAlreadyRegisteredException.class, () -> createAttendantUseCase.execute(request));

        verify(attendantsRepository, times(1)).findByEmail(request.email());
        verifyNoMoreInteractions(attendantsRepository);
        verifyNoInteractions(attendantQueueProducer);
    }

}
