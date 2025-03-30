package org.example.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.transactionservice.models.Transaction;
import org.example.transactionservice.models.TransactionType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Long id;
    private Long categoryId;

    private String title;
    private TransactionType type;
    private Double value;
    private LocalDateTime time;


    public static TransactionDTO fromTransaction(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getCategory().getId(),
                transaction.getTitle(),
                transaction.getType(),
                transaction.getValue(),
                transaction.getTime()
        );
    }
}
