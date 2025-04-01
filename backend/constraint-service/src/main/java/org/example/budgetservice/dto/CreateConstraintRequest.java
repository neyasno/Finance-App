package org.example.budgetservice.dto;

import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

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
