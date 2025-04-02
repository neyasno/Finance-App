package org.example.budgetservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteConstraintRequest {
    @NotNull
    private Long id;
}
