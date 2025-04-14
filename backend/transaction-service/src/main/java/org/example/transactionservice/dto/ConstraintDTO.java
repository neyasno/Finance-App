package org.example.transactionservice.dto;

import lombok.Data;

@Data
public class ConstraintDTO {
    private Long id;
    private Long categoryId;

    private Double available;
}
