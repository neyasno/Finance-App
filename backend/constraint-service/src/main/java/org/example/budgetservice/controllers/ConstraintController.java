package org.example.budgetservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.budgetservice.clients.NotificationServiceClient;
import org.example.budgetservice.dto.CreateConstraintRequest;
import org.example.budgetservice.dto.UpdateConstraintRequest;
import org.example.budgetservice.exceptions.ConstraintNotFoundException;
import org.example.budgetservice.models.Constraint;
import org.example.budgetservice.services.ConstraintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ConstraintController {
    private final ConstraintService constraintService;
    private final NotificationServiceClient notificationServiceClient;

    private final static String X_USER_ID = "X-User-Id";

    @GetMapping("/{id}")
    public ResponseEntity<Constraint> getConstraint(
            @PathVariable Long id,
            @RequestHeader(X_USER_ID) Long userId
    ) {
        try {
            Constraint data = constraintService.getConstraint(id, userId);

            return ResponseEntity.ok(data);
        } catch (ConstraintNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<Constraint>> getConstraintsByCategory(
            @PathVariable Long categoryId,
            @RequestHeader(X_USER_ID) Long userId
    ) {
        try {
            List<Constraint> constraints = constraintService.getConstraintsByCategory(categoryId, userId);

            return ResponseEntity.ok(constraints);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/by-category/{categoryId}/check-limits/")
    public ResponseEntity<Void> checkConstraintLimitsForCategory(
            @PathVariable Long categoryId,
            @RequestHeader(X_USER_ID) Long userId
    ) {
        List<Constraint> constraintsByCategory = constraintService.getConstraintsByCategory(categoryId, userId);

        constraintsByCategory.stream()
                .filter(it -> it.getAvailable() <= 0)
                .findFirst()
                .ifPresent(constraint -> notificationServiceClient.createLimitNotification(userId, constraint));

        return ResponseEntity.noContent().build();
    }

    @GetMapping("")
    public ResponseEntity<List<Constraint>> getAllConstraints(
            @RequestHeader(name = X_USER_ID) Long userId
    ) {
        try {
            List<Constraint> data = constraintService.getAllConstraints(userId);

            return ResponseEntity.ok(data);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Constraint> createConstraint(
            @RequestBody @Valid CreateConstraintRequest request,
            @RequestHeader(name = X_USER_ID) Long userId
    ) {
        try {
            Constraint constraintData = Constraint.builder()
                    .value(request.getValue())
                    .timeToExpire(request.getExpirationTime())
                    .timeCreated(LocalDateTime.now())
                    .categoryId(request.getCategoryId())
                    .userId(userId).build();

            Constraint data = constraintService.createConstraint(constraintData);

            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Constraint> updateConstraint(
            @RequestBody @Valid UpdateConstraintRequest request,
            @PathVariable Long id,
            @RequestHeader(X_USER_ID) Long userId
    ) {
        try {
            Constraint item = constraintService.getConstraint(id, userId);

            if (request.getTime() != null) {
                item.setTimeToExpire(request.getTime());
            }
            if (request.getValue() != null) {
                item.setValue(request.getValue());
            }

            if (request.getCategoryId() != null) {
                item.setCategoryId(request.getCategoryId());
            }

            Constraint data = constraintService.updateConstraint(item);

            return ResponseEntity.ok(data);
        } catch (ConstraintNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConstraint(
            @PathVariable Long id,
            @RequestHeader(X_USER_ID) Long userId
    ) {
        try {
            constraintService.deleteConstraint(id, userId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
