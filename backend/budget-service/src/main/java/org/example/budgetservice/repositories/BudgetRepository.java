package org.example.budgetservice.repositories;

import org.example.budgetservice.models.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    // Отложено по отсутствию требований. Согласовано с тех-илдом aka. Kostya GreenSheen
}
