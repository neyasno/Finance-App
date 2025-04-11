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

    public void deleteConstraint(Long id, Long userId) {
        constraintRepository.deleteByIdAndUserId(id, userId);
    }

    public Constraint getConstraint(Long id, Long userId) {
        Constraint constraint = constraintRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ConstraintNotFoundException("Constraint not found"));
        fillConstraintData(constraint);

        return constraint;
    }

    public List<Constraint> getAllConstraints(Long userId) {
        List<Constraint> result = constraintRepository.findAllByUserId(userId);
        result.forEach(this::fillConstraintData);

        return result;
    }

    public List<Constraint> getConstraintsByCategory(Long categoryId, Long userId) {
        List<Constraint> constraints = constraintRepository.findAllByUserIdAndCategoryId(userId, categoryId);
        constraints.forEach(this::fillConstraintData);

        return constraints;
    }

    private void fillConstraintData(Constraint constraint) {
        try {
            ResponseEntity<List<TransactionDTO>> response = transactionServiceClient.getAllTransactionsBetween(constraint.getUserId(), constraint.getTimeCreated(), constraint.getTimeToExpire());

            if (!response.hasBody() || response.getBody().isEmpty()) {
                constraint.setAvailable(constraint.getValue());
                return;
            }

            List<TransactionDTO> transactions = response.getBody();

            if(constraint.getCategoryId() != null) {

                log.debug("Category Id: {} for constraint : {}", constraint.getCategoryId(), constraint);

                transactions = transactions.stream().filter(transaction -> transaction.getCategoryId().equals(constraint.getCategoryId())).toList();
            }

            fillConstraintData(constraint, transactions);

        } catch (Exception e) {
            log.error("ERROR DURING: <fillConstraintData>: {}", e.getMessage());
            throw new RuntimeException("Error while filling constraint", e);
        }
    }

    private void fillConstraintData(Constraint constraint, List<TransactionDTO> transactions) {
        Double sum = transactions != null ?
                transactions.stream()
                        .map(transaction -> (transaction.getType().equalsIgnoreCase("income") ? 1 : -1) * transaction.getValue())
                        .reduce(Double::sum)
                        .orElse(0.0)
                : 0.0;

        constraint.setAvailable(constraint.getValue() + sum);
    }
}