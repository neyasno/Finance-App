package org.example.budgetservice.services;

import lombok.RequiredArgsConstructor;
import org.example.budgetservice.models.Constraint;
import org.example.budgetservice.repositories.ConstraintRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstraintService {

    private final ConstraintRepository constraintRepository;

    public Constraint updateConstraint(Constraint constraint) {
        Constraint item = constraintRepository.findById(constraint.getId()).orElse(null);
        if (item == null) {
            throw new RuntimeException("Constraint not found");
        }
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
        return constraintRepository.findById(id).orElseThrow(() -> new RuntimeException("Constraint not found"));
    }

    public List<Constraint> getAllConstraints() {
        return constraintRepository.findAll();
    }
}