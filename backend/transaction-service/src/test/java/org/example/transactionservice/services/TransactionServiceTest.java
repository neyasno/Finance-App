package org.example.transactionservice.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.transactionservice.dto.SaveTransactionRequest;
import org.example.transactionservice.models.Category;
import org.example.transactionservice.models.Transaction;
import org.example.transactionservice.models.TransactionType;
import org.example.transactionservice.repositories.CategoryRepository;
import org.example.transactionservice.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;
    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category(1L, "Test Category", null, 0.0, 0.0, 0.0, 0.0);
        transaction = Transaction.builder()
                .id(1L)
                .userId(1L)
                .title("Test Transaction")
                .value(100.0)
                .type(TransactionType.INCOME)
                .time(LocalDateTime.now())
                .category(category)
                .build();
    }

    @Test
    void createTransaction_ShouldReturnTransaction() {
        SaveTransactionRequest request = new SaveTransactionRequest(1L, "Test Transaction", 100.0, "INCOME");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction savedTransaction = transactionService.createTransaction(request, 1L);

        assertNotNull(savedTransaction);
        assertEquals("Test Transaction", savedTransaction.getTitle());
    }

    @Test
    void createTransaction_ShouldThrowException_WhenCategoryNotFound() {
        SaveTransactionRequest request = new SaveTransactionRequest(1L, "Test Transaction", 100.0, "INCOME");
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> transactionService.createTransaction(request, 1L));
    }

    @Test
    void getTransactionById_ShouldReturnTransaction() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Transaction foundTransaction = transactionService.getTransactionById(1L);

        assertNotNull(foundTransaction);
        assertEquals(1L, foundTransaction.getId());
    }

    @Test
    void getTransactionById_ShouldThrowException_WhenNotFound() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> transactionService.getTransactionById(1L));
    }

    @Test
    void getAllTransactionsPaginatedForUser_ShouldReturnPage() {
        Page<Transaction> page = new PageImpl<>(List.of(transaction));
        when(transactionRepository.findAllByUserId(any(PageRequest.class), eq(1L))).thenReturn(page);

        Page<Transaction> result = transactionService.getAllTransactionsPaginatedForUser(0, 10, 1L);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

//    @Test
//    void deleteTransactionById_ShouldReturnFalseAfterDeletion() {
//        when(transactionRepository.existsById(1L)).thenReturn(false);
//        doNothing().when(transactionRepository).deleteById(1L);
//
//        boolean existsAfterDeletion = transactionService.deleteTransactionById(1L);
//        assertFalse(existsAfterDeletion);
//    }
}
