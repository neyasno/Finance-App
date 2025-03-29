package org.example.budgetservice.services;

import lombok.RequiredArgsConstructor;
import org.example.budgetservice.models.Constraint;
import org.example.budgetservice.repositories.ConstraintRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstraintService {

    private final ConstraintRepository constraintRepository;

    public Constraint updateConstraint(Long id, Double value) {
        Constraint constraint = constraintRepository.findById(id).orElse(null);
        if (constraint == null) {
            throw new RuntimeException("Constraint not found");
        }
        constraint.setValue(value);
        return constraintRepository.save(constraint);
    }

    public Constraint createConstraint(Constraint constraint) {
        return constraintRepository.save(constraint);
    }

    public Boolean deleteConstraint(Long id) {
        Constraint constraint = constraintRepository.findById(id).orElse(null);

        if(constraint == null) {
            return false;
        }

        constraintRepository.delete(constraint);

        return true;
    }

    public Constraint getConstraint(Long id) {
        Constraint constraint = constraintRepository.findById(id).orElse(null);

        if(constraint == null) {
            throw new RuntimeException("Constraint not found");
        }

        return constraint;
    }
}