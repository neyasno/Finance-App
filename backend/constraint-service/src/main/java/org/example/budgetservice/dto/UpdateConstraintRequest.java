package org.example.budgetservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class UpdateConstraintRequest {
    @NotNull
    @Min(0)
    private Double value;

    @DateTimeFormat(pattern = "HH:mm:ss dd.MM.yyyy")
    private LocalDateTime time;

    private Long categoryId;
}
