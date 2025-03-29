package org.example.budgetservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "budgets")
public class Budget {
    // Отложено по отсутствию требований. Согласовано с тех-илдом aka. Kostya GreenSheen
}
