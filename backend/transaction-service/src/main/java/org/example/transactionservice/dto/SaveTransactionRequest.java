package org.example.transactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.transactionservice.models.TransactionType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveTransactionRequest {
    private Long categoryId;

    private String title;
    private Double value;
    private TransactionType type;
}
