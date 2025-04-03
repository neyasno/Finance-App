package org.example.transactionservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveTransactionRequest {
    @NotNull
    private Long categoryId;

    @NotNull
    private String title;

    @NotNull
    @Min(0)
    private Double value;

    @NotNull
    private String type;
}
