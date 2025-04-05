package org.example.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.transactionservice.models.Transaction;
import org.example.transactionservice.models.TransactionType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Long id;
    private Long categoryId;

    private String title;
    private Double value;
    private String type;
    private LocalDateTime time;


    public static TransactionDTO fromTransaction(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId() != null ?transaction.getId() : null,
                transaction.getCategory() != null ? transaction.getCategory().getId() : null,
                transaction.getTitle() != null ?transaction.getTitle() : null,
                transaction.getValue() != null ?transaction.getValue() : null,
                transaction.getType() != null ? transaction.getType().toString().toLowerCase() : null,
                transaction.getTime() != null ? transaction.getTime() : null
        );
    }

    public static List<TransactionDTO> fromTransactions(List<Transaction> transactions) {
        return transactions.stream().map(TransactionDTO::fromTransaction).toList();
    }
}
