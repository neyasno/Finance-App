package org.example.budgetservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateConstraintRequest {
    @NotNull
    private Double value;
    @NotNull
    private Long id;
    @NotNull
    private LocalDateTime time;
}
