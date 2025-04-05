package org.example.transactionservice.services;

import lombok.RequiredArgsConstructor;
import org.example.transactionservice.dto.SaveTransactionRequest;
import org.example.transactionservice.models.Category;
import org.example.transactionservice.models.Transaction;
import org.example.transactionservice.models.TransactionType;
import org.example.transactionservice.repositories.CategoryRepository;
import org.example.transactionservice.repositories.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public Transaction createTransaction(SaveTransactionRequest request, Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }

        Long categoryId = request.getCategoryId();

        if (categoryId == null) {
            throw new IllegalArgumentException("Category id cannot be null");
        }

        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            throw new IllegalArgumentException("Category does not exist");
        }

        Transaction transaction = Transaction.builder()
                .userId(userId)
                .title(request.getTitle())
                .value(request.getValue())
                .type(TransactionType.valueOf(request.getType().toUpperCase()))
                .time(LocalDateTime.now())
                .category(category.get())
                .build();

        transaction = transactionRepository.save(transaction);

        return transaction;
    }

    public Transaction updateTransaction(SaveTransactionRequest request, Long transactionId) {
        Long categoryId = request.getCategoryId();

        if (categoryId == null) {
            throw new IllegalArgumentException("Category id cannot be null");
        }

        if (!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Category does not exist");
        }

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();

        transaction.setTitle(request.getTitle());
        transaction.setValue(request.getValue());
        transaction.setType(TransactionType.valueOf(request.getType().toUpperCase()));
        transaction.setTime(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);

        return transaction;
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
    }

    public Page<Transaction> getAllTransactionsPaginatedForUser(Integer pageNumber, Integer pageSize, Long userId) {
        return transactionRepository.findAllByUserId(Pageable.ofSize(pageSize).withPage(pageNumber), userId);
    }

    public List<Transaction> getAllTransactionsBefore(LocalDateTime time, Long userId) {
        time = fixTimeZone(time);
        return transactionRepository.findAllByTimeBeforeAndUserId(time, userId);
    }

    public List<Transaction> getAllTransactionsAfter(LocalDateTime time, Long userId) {
        time = fixTimeZone(time);
        return transactionRepository.findAllByTimeAfterAndUserId(time, userId);
    }

    public List<Transaction> getAllTransactionsBetween(LocalDateTime start, LocalDateTime end, Long userId) {
        start = fixTimeZone(start);
        end = fixTimeZone(end);
        return transactionRepository.findAllByTimeBetweenAndUserId(start, end, userId);
    }

    public List<Transaction> getTransactionsByCategoryId(Long categoryId) {
        return transactionRepository.findAllByCategoryId(categoryId);
    }

    public void deleteTransactionById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    private LocalDateTime fixTimeZone(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    public List<Transaction> getAllTransactionsForUser(Long userId) {
        return transactionRepository.findAllByUserId(userId);
    }
}
