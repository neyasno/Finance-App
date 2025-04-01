package org.example.budgetservice.services;

import lombok.RequiredArgsConstructor;
import org.example.budgetservice.clients.TransactionServiceClient;
import org.example.budgetservice.exceptions.ConstraintNotFoundException;
import org.example.budgetservice.models.Constraint;
import org.example.budgetservice.repositories.ConstraintRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConstraintService {

    private final ConstraintRepository constraintRepository;
    private final TransactionServiceClient transactionServiceClient;

    public Constraint updateConstraint(Constraint constraint) {
        Constraint item = constraintRepository.findById(constraint.getId()).orElse(null);

        if (item == null) {
            throw new ConstraintNotFoundException("Constraint not found");
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
        return constraintRepository.findById(id).orElseThrow(() -> new ConstraintNotFoundException("Constraint not found"));
    }

    public List<Constraint> getAllConstraints(Long userId) {
        List<Constraint> result = constraintRepository.findAllByUserId(userId);

        //TODO: ЛОГИКА КЛИЕНТА, РЕАЛИЗОВАТЬ ТУТ В ЦИКЛЕ. ПРОСТО ДОБАВЛЯЕМ ПОЛЕ ДЛЯ КОНСТРЕЙНТОВ :)))
        //for(Constraint item : result) {

            //    item.setAvailable(transactionServiceClient.get);

            //}

        return result;
    }
}