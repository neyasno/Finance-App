package org.example.transactionservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.transactionservice.dto.SaveTransactionRequest;
import org.example.transactionservice.models.Transaction;
import org.example.transactionservice.services.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Page<Transaction>> getAllTransactionsPaginated(
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer pageNumber,
            @RequestParam(name = "size", defaultValue = "10", required = false) Integer pageSize
    ) {
        return ResponseEntity.ok(transactionService.getAllTransactions(pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody SaveTransactionRequest request, @RequestHeader(name = X_USER_ID) Long userId) {
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
    public ResponseEntity<Transaction> updateTransaction(@PathVariable Long transactionId, @RequestBody SaveTransactionRequest request) {
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
