package org.example.transactionservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transactionservice.dto.SaveTransactionRequest;
import org.example.transactionservice.models.Transaction;
import org.example.transactionservice.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionsController {
    private final static String X_USER_ID = "X-User-Id";

    private final TransactionService transactionService;

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable Long transactionId) {
        if (transactionId == null) {
            return ResponseEntity.badRequest().build();
        }

        Transaction transaction;

        try {
            transaction = transactionService.getTransactionById(transactionId);
        } catch (Exception e) {
            log.error("GET_TRANSACTION_BY_ID ({}): {}", transactionId, e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/by-time")
    public ResponseEntity<List<Transaction>> getTransactions(
            @RequestParam(required = false) @DateTimeFormat LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat LocalDateTime to,
            @RequestHeader(X_USER_ID) Long userId) {

        if (from == null && to == null) {
            return ResponseEntity.badRequest().build();
        } else if (from == null) {
            return ResponseEntity.ok(transactionService.getAllTransactionsBefore(to, userId));
        } else if (to == null) {
            return ResponseEntity.ok(transactionService.getAllTransactionsAfter(from, userId));
        } else {
            return ResponseEntity.ok(transactionService.getAllTransactionsBetween(from, to, userId));
        }
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<Transaction>> getTransactionsByCategory(@PathVariable Long categoryId) {
        if (categoryId == null) {
            return ResponseEntity.badRequest().build();
        }

        try {
            return ResponseEntity.ok(transactionService.getTransactionsByCategoryId(categoryId));
        }catch (Exception e) {
            log.error("GET_TRANSACTION_BY_CATEGORY ({}): {}", categoryId, e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions(
            @RequestHeader(X_USER_ID) Long userId
    ){
        return ResponseEntity.ok(transactionService.getAllTransactionsForUser(userId));
    }

    @GetMapping
    public ResponseEntity<Page<Transaction>> getAllTransactionsPaginated(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer pageSize,
            @RequestHeader(X_USER_ID) Long userId
    ) {
        return ResponseEntity.ok(transactionService.getAllTransactionsPaginatedForUser(pageNumber, pageSize, userId));
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Valid SaveTransactionRequest request, @RequestHeader(name = X_USER_ID) Long userId) {
        Transaction transaction;

        try {
            transaction = transactionService.createTransaction(request, userId);
        } catch (Exception e) {
            log.error("CREATE_TRANSACTION: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(transaction);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long transactionId, @RequestBody @Valid SaveTransactionRequest request) {
        Transaction transaction;

        try {
            transaction = transactionService.updateTransaction(request, transactionId);
        } catch (Exception e) {
            log.error("UPDATE_TRANSACTION({}): {}", transactionId, e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        if (transactionId == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean deleted = transactionService.deleteTransactionById(transactionId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
