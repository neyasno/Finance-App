package org.example.transactionservice.services;

import lombok.RequiredArgsConstructor;
import org.example.transactionservice.dto.SaveTransactionRequest;
import org.example.transactionservice.models.Transaction;
import org.example.transactionservice.repositories.CategoryRepository;
import org.example.transactionservice.repositories.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;

    public Transaction createTransaction(SaveTransactionRequest request, Long userId) {
        if(userId == null){
            throw new IllegalArgumentException("userId cannot be null");
        }

        Long categoryId = request.getCategoryId();

        if (categoryId == null) {
            throw new IllegalArgumentException("Category id cannot be null");
        }

        if(!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Category does not exist");
        }
        Transaction transaction = Transaction.builder()
                .userId(userId)
                .title(request.getTitle())
                .value(request.getValue())
                .type(request.getType())
                .time(LocalDateTime.now())
                .build();

        transaction = transactionRepository.save(transaction);

        return transaction;
    }

    public Transaction updateTransaction(SaveTransactionRequest request, Long transactionId) {
        Long categoryId = request.getCategoryId();

        if (categoryId == null) {
            throw new IllegalArgumentException("Category id cannot be null");
        }

        if(!categoryRepository.existsById(categoryId)) {
            throw new IllegalArgumentException("Category does not exist");
        }

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();

        transaction.setTitle(request.getTitle());
        transaction.setValue(request.getValue());
        transaction.setType(request.getType());
        transaction.setTime(LocalDateTime.now());

        transaction = transactionRepository.save(transaction);

        return transaction;
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Page<Transaction> getAllTransactions(Integer pageNumber, Integer pageSize) {
        return transactionRepository.findAll(Pageable.ofSize(pageSize).withPage(pageNumber));
    }

    public List<Transaction> getAllTransactionsBefore(LocalDateTime time, Long userId) {
        return transactionRepository.findAllByTimeBeforeAndUserId(time, userId);
    }

    public List<Transaction> getAllTransactionsAfter(LocalDateTime time, Long userId) {
        return transactionRepository.findAllByTimeAfterAndUserId(time, userId);
    }

    public List<Transaction> getAllTransactionsBetween(LocalDateTime start, LocalDateTime end, Long userId) {
        return transactionRepository.findAllByTimeBetweenAndUserId(start, end, userId);
    }

    public List<Transaction> getTransactionsByCategoryId(Long categoryId) {
        return transactionRepository.findAllByCategoryId(categoryId);
    }

    public boolean deleteTransactionById(Long transactionId) {
        transactionRepository.deleteById(transactionId);
        return transactionRepository.existsById(transactionId);
    }

}
