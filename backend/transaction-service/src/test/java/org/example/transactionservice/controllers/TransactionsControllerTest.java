package org.example.transactionservice.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.transactionservice.dto.SaveTransactionRequest;
import org.example.transactionservice.models.Transaction;
import org.example.transactionservice.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionsControllerTest {

    private static final String X_USER_ID = "X-User-Id";

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionsController transactionsController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionsController).build();
    }

    @Test
    void testGetTransaction_Success() throws Exception {
        Long transactionId = 1L;
        Transaction transaction = Transaction.builder()
                .id(transactionId)
                .title("Sample Transaction")
                .build();

        when(transactionService.getTransactionById(transactionId)).thenReturn(transaction);

        mockMvc.perform(get("/" + transactionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(transactionId))
                .andExpect(jsonPath("$.title").value("Sample Transaction"));

        verify(transactionService, times(1)).getTransactionById(transactionId);
    }

    @Test
    void testGetTransaction_NotFound() throws Exception {
        Long transactionId = 1L;

        when(transactionService.getTransactionById(transactionId)).thenThrow(new RuntimeException("Not Found"));

        mockMvc.perform(get("/" + transactionId))
                .andExpect(status().isBadRequest());

        verify(transactionService, times(1)).getTransactionById(transactionId);
    }

    @Test
    void testGetTransactions_Before() throws Exception {
        LocalDateTime to = LocalDateTime.of(2025, 4, 3, 12, 0, 0);
        Long userId = 123L;

        Transaction transaction = Transaction.builder()
                .id(1L)
                .title("Test To")
                .build();

        List<Transaction> transactions = List.of(transaction);

        when(transactionService.getAllTransactionsBefore(to, userId)).thenReturn(transactions);

        mockMvc.perform(get("/by-time")
                        .param("to", "2025-04-03T12:00:00")
                        .header(X_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(transactionService, times(1)).getAllTransactionsBefore(to, userId);
    }

    @Test
    void testGetTransactions_After() throws Exception {
        LocalDateTime from = LocalDateTime.of(2025, 4, 3, 12, 0, 0);
        Long userId = 123L;

        Transaction transaction = Transaction.builder()
                .id(1L)
                .title("Test After")
                .build();

        List<Transaction> transactions = List.of(transaction);

        when(transactionService.getAllTransactionsAfter(from, userId)).thenReturn(transactions);

        mockMvc.perform(get("/by-time")
                        .param("from", "2025-04-03T12:00:00")
                        .header(X_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(transactionService, times(1)).getAllTransactionsAfter(from, userId);
    }

    @Test
    void testGetTransactions_Between() throws Exception {
        LocalDateTime from = LocalDateTime.of(2025, 4, 3, 10, 0, 0);
        LocalDateTime to = LocalDateTime.of(2025, 4, 3, 12, 0, 0);
        Long userId = 123L;

        Transaction transaction = Transaction.builder()
                .id(1L)
                .title("Test Between")
                .build();

        List<Transaction> transactions = List.of(transaction);

        when(transactionService.getAllTransactionsBetween(from, to, userId)).thenReturn(transactions);

        mockMvc.perform(get("/by-time")
                        .param("from", "2025-04-03T10:00:00")
                        .param("to", "2025-04-03T12:00:00")
                        .header(X_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

        verify(transactionService, times(1)).getAllTransactionsBetween(from, to, userId);
    }

    @Test
    void testCreateTransaction_Success() throws Exception {
        Long userId = 123L;
        SaveTransactionRequest request = new SaveTransactionRequest(1L, "New Transaction", 100.0, "income");
        Transaction savedTransaction = Transaction.builder()
                .id(1L)
                .title("New Transaction")
                .build();

        when(transactionService.createTransaction(any(SaveTransactionRequest.class), eq(userId))).thenReturn(savedTransaction);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(X_USER_ID, userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Transaction"));

        verify(transactionService, times(1)).createTransaction(any(SaveTransactionRequest.class), eq(userId));
    }

    @Test
    void testUpdateTransaction_Success() throws Exception {
        Long transactionId = 1L;
        SaveTransactionRequest request = new SaveTransactionRequest(1L, "Updated Transaction", 200.0, "income");
        Transaction updatedTransaction = Transaction.builder()
                .id(transactionId)
                .title("Updated Transaction")
                .build();

        when(transactionService.updateTransaction(any(SaveTransactionRequest.class), eq(transactionId))).thenReturn(updatedTransaction);

        mockMvc.perform(put("/" + transactionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Transaction"));

        verify(transactionService, times(1)).updateTransaction(any(SaveTransactionRequest.class), eq(transactionId));
    }

    @Test
    void testDeleteTransaction_Success() throws Exception {
        Long transactionId = 1L;

        doNothing().when(transactionService).deleteTransactionById(transactionId);

        mockMvc.perform(delete("/" + transactionId))
                .andExpect(status().isNoContent());

        verify(transactionService, times(1)).deleteTransactionById(transactionId);
    }

//    @Test
//    void testDeleteTransaction_NotFound() throws Exception {
//        Long transactionId = 1L;
//
//        when(transactionService.deleteTransactionById(transactionId)).thenReturn(false);
//
//        mockMvc.perform(delete("/" + transactionId))
//                .andExpect(status().isNotFound());
//
//        verify(transactionService, times(1)).deleteTransactionById(transactionId);
//    }
}
