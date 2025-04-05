package org.example.analyticsservice.dto;

import lombok.Data;

@Data
public class TransactionDTO {
    private String title;
    private Double value;
    private String type;
    private Long categoryId;
}
