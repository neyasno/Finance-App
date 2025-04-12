package org.example.budgetservice.repositories;

import org.example.budgetservice.models.Constraint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConstraintRepository extends JpaRepository<Constraint, Long> {
    List<Constraint> findAllByUserId(Long userId);

    Optional<Constraint> findByIdAndUserId(Long id, Long id1);

    @Transactional
    void deleteByIdAndUserId(Long id, Long userId);

    List<Constraint> findAllByUserIdAndCategoryId(Long userId, Long categoryId);
}