package org.example.budgetservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateConstraintRequest {
    @NotNull
    @Min(0)
    private Double value;

    private LocalDateTime time;
}
