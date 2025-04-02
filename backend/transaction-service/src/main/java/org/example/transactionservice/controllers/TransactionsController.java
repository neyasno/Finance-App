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
@RequestMapping("/transactions")
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

    @GetMapping("/before")
    public ResponseEntity<List<Transaction>> getAllTransactionsBefore(
            @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time,
            @RequestHeader(X_USER_ID) Long userId) {

        if (time == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(transactionService.getAllTransactionsBefore(time, userId));
    }

    @GetMapping("/after")
    public ResponseEntity<List<Transaction>> getAllTransactionsAfter(
            @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time,
            @RequestHeader(X_USER_ID) Long userId) {

        if (time == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(transactionService.getAllTransactionsAfter(time, userId));
    }

    @GetMapping("/between")
    public ResponseEntity<List<Transaction>> getAllTransactionsBetween(
            @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime before,
            @RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime after,
            @RequestHeader(X_USER_ID) Long userId) {

        if (before == null || after == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(transactionService.getAllTransactionsBetween(before, after, userId));
    }

    @GetMapping
    public ResponseEntity<Page<Transaction>> getAllTransactionsPaginated(
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer pageSize,
            @RequestHeader(X_USER_ID) Long userId
    ) {
        return ResponseEntity.ok(transactionService.getAllTransactionsForUser(pageNumber, pageSize, userId));
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
}
