package org.example.budgetservice.services;

import lombok.RequiredArgsConstructor;
import org.example.budgetservice.repositories.ConstraintRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetService budgetService;
    // Отложено по отсутствию требований. Согласовано с тех-илдом aka. Kostya GreenSheen
}
