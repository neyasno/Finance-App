package org.example.budgetservice.dto;

import lombok.Data;

@Data
public class DeleteConstraintRequest {
    @NotNull
    private Long id;
}
