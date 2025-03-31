package org.example.budgetservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateConstraintRequest {
    private Double value;
    private Long id;
    private LocalDateTime time;
}
