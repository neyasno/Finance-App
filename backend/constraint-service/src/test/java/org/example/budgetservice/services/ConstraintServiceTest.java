package org.example.budgetservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.budgetservice.clients.TransactionServiceClient;
import org.example.budgetservice.dto.TransactionDTO;
import org.example.budgetservice.exceptions.ConstraintNotFoundException;
import org.example.budgetservice.models.Constraint;
import org.example.budgetservice.repositories.ConstraintRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ConstraintServiceTest {

    @Mock
    private ConstraintRepository constraintRepository;

    @Mock
    private TransactionServiceClient transactionServiceClient;

    @InjectMocks
    private ConstraintService constraintService;

    private Constraint constraint;


    @BeforeEach
    void setUp() {
        constraint = Constraint.builder()
                .id(1L)
                .value(500.0)
                .timeToExpire(LocalDateTime.now().plusDays(30))
                .userId(1L)
                .available(0.0)
                .build();
    }

    @Test
    void createConstraint_ShouldReturnSavedConstraint() {
        when(constraintRepository.save(any(Constraint.class))).thenReturn(constraint);
        when(transactionServiceClient.getAllTransactionsBetween(eq(1L), any(), any())).thenReturn(ResponseEntity.ok(Collections.emptyList()));

        Constraint savedConstraint = constraintService.createConstraint(constraint);

        assertNotNull(savedConstraint);
        assertEquals(500.0, savedConstraint.getValue());
        assertEquals(constraint.getId(), savedConstraint.getId());
    }

    @Test
    void updateConstraint_ShouldReturnUpdatedConstraint() {
        when(constraintRepository.findById(1L)).thenReturn(Optional.of(constraint));
        when(constraintRepository.save(any(Constraint.class))).thenReturn(constraint);
        when(transactionServiceClient.getAllTransactionsBetween(eq(1L), any(), any())).thenReturn(ResponseEntity.ok(Collections.emptyList()));

        constraint.setValue(600.0);
        constraint.setTimeToExpire(LocalDateTime.now().plusDays(60));

        Constraint updatedConstraint = constraintService.updateConstraint(constraint);

        assertNotNull(updatedConstraint);
        assertEquals(600.0, updatedConstraint.getValue());
        assertEquals(1L, updatedConstraint.getId());
    }

    @Test
    void updateConstraint_ShouldThrowException_WhenConstraintNotFound() {
        when(constraintRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ConstraintNotFoundException.class, () -> constraintService.updateConstraint(constraint));
    }

    @Test
    void getConstraint_ShouldReturnConstraint() {
        when(constraintRepository.findById(1L)).thenReturn(Optional.of(constraint));
        when(transactionServiceClient.getAllTransactionsBetween(eq(1L), any(), any())).thenReturn(ResponseEntity.ok(Collections.emptyList()));

        Constraint foundConstraint = constraintService.getConstraint(1L);

        assertNotNull(foundConstraint);
        assertEquals(1L, foundConstraint.getId());
        assertEquals(500.0, foundConstraint.getValue());
    }

    @Test
    void getConstraint_ShouldThrowException_WhenNotFound() {
        when(constraintRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ConstraintNotFoundException.class, () -> constraintService.getConstraint(1L));
    }

    @Test
    void getConstraint_ShouldFillAvailableAmountCorrectly() {
        List<TransactionDTO> transactions = List.of(
                new TransactionDTO(1L, 1L, "Salary", "income", 200.0, LocalDateTime.now()),
                new TransactionDTO(2L, 1L, "Rent", "outcome", 50.0, LocalDateTime.now())
        );

        when(constraintRepository.findById(1L)).thenReturn(Optional.of(constraint));
        when(transactionServiceClient.getAllTransactionsBetween(eq(1L), any(), any())).thenReturn(ResponseEntity.ok(transactions));

        Constraint foundConstraint = constraintService.getConstraint(1L);

        assertNotNull(foundConstraint);
        assertEquals(1L, foundConstraint.getId());
        assertEquals(500.0 + 200.0 - 50.0, foundConstraint.getAvailable()); // Value + (income - outcome)
    }

    @Test
    void deleteConstraint_ShouldReturnTrue_WhenDeleted() {
        when(constraintRepository.findById(1L)).thenReturn(Optional.of(constraint));
        doNothing().when(constraintRepository).delete(constraint);

        boolean deleted = constraintService.deleteConstraint(1L);
        assertTrue(deleted);
    }

    @Test
    void deleteConstraint_ShouldReturnFalse_WhenNotFound() {
        when(constraintRepository.findById(1L)).thenReturn(Optional.empty());

        boolean deleted = constraintService.deleteConstraint(1L);
        assertFalse(deleted);
    }

    @Test
    void getAllConstraints_ShouldReturnConstraintsWithAvailableValues() {
        List<Constraint> constraints = List.of(constraint);
        List<TransactionDTO> transactions = List.of(
                new TransactionDTO(1L, 1L, "Salary", "income", 200.0, LocalDateTime.now()),
                new TransactionDTO(2L, 1L, "Rent", "outcome", 50.0, LocalDateTime.now())
        );

        when(constraintRepository.findAllByUserId(1L)).thenReturn(constraints);
        when(transactionServiceClient.getAllTransactionsBetween(eq(1L), any(), any())).thenReturn(ResponseEntity.ok(transactions));

        List<Constraint> result = constraintService.getAllConstraints(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(500.0 + 200.0 - 50.0, result.get(0).getAvailable()); // Value + income - outcome
    }

    @Test
    void getAllConstraints_ShouldHandleError_WhenTransactionClientFails() {
        List<Constraint> constraints = List.of(constraint);

        when(constraintRepository.findAllByUserId(1L)).thenReturn(constraints);
        when(transactionServiceClient.getAllTransactionsBetween(eq(1L), any(), any()))
                .thenThrow(new RuntimeException("Error getting transactions"));

        assertThrows(RuntimeException.class, () -> constraintService.getAllConstraints(1L));
    }
}