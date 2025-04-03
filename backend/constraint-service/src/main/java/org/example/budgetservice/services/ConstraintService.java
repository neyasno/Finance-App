package org.example.budgetservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.budgetservice.clients.TransactionServiceClient;
import org.example.budgetservice.dto.TransactionDTO;
import org.example.budgetservice.exceptions.ConstraintNotFoundException;
import org.example.budgetservice.models.Constraint;
import org.example.budgetservice.repositories.ConstraintRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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

        item = constraintRepository.save(constraint);
        fillConstraintData(item);

        return item;
    }

    public Constraint createConstraint(Constraint constraint) {
        Constraint createdConstraint = constraintRepository.save(constraint);
        fillConstraintData(createdConstraint);
        return createdConstraint;
    }

    public Boolean deleteConstraint(Long id) {
        Constraint constraint = constraintRepository.findById(id).orElse(null);

        if (constraint == null) {
            return false;
        }

        constraintRepository.delete(constraint);

        return true;
    }

    public Constraint getConstraint(Long id) {
        Constraint constraint = constraintRepository.findById(id).orElseThrow(() -> new ConstraintNotFoundException("Constraint not found"));
        fillConstraintData(constraint);

        return constraint;
    }

    public List<Constraint> getAllConstraints(Long userId) {
        List<Constraint> result = constraintRepository.findAllByUserId(userId);
        result.forEach(this::fillConstraintData);

        return result;
    }

    private void fillConstraintData(Constraint constraint) {
        try {
            ResponseEntity<List<TransactionDTO>> response = transactionServiceClient.getAllTransactionsBetween(constraint.getUserId(), constraint.getTimeCreated(), constraint.getTimeToExpire());

            if (!response.hasBody()) {
                constraint.setAvailable(constraint.getValue());
                return;
            }

            List<TransactionDTO> transactions = response.getBody();

            Double sum = transactions != null ? transactions.stream().map(transaction -> (transaction.getType().equalsIgnoreCase("income") ? 1 : -1) * transaction.getValue()).reduce(Double::sum).orElse(0.0) : 0.0;

            constraint.setAvailable(constraint.getValue() + sum);
        } catch (Exception e) {
            log.error("ERROR DURING: <fillConstraintData>: {}", e.getMessage());
            throw new RuntimeException("Error while filling constraint", e);
        }
    }
}