package org.example.budgetservice.repositories;

import org.example.budgetservice.models.Constraint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConstraintRepository extends JpaRepository<Constraint, Long> {
    List<Constraint> findAllByUserId(Long userId);
}