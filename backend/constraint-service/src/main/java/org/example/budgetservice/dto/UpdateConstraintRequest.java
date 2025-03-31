package org.example.budgetservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateConstraintRequest {
    private Double value;
    private LocalDateTime time;
}
