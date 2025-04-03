package org.example.budgetservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class CreateConstraintRequest {
    @NotNull
    private Double value;

    @NotNull
    @DateTimeFormat
    private LocalDateTime expirationTime;
}
