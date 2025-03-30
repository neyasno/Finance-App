package org.example.transactionservice.repositories;

import org.example.transactionservice.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByCategoryId(Long categoryId);
    List<Transaction> findAllByTimeBeforeAndUserId(LocalDateTime before, Long userId);
    List<Transaction> findAllByTimeAfterAndUserId(LocalDateTime after, Long userId);
    List<Transaction> findAllByTimeBetweenAndUserId(LocalDateTime before, LocalDateTime after, Long userId);
}

