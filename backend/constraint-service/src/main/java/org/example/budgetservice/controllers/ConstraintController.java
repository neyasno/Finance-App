package org.example.budgetservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.budgetservice.dto.CreateConstraintRequest;
import org.example.budgetservice.dto.UpdateConstraintRequest;
import org.example.budgetservice.models.Constraint;
import org.example.budgetservice.services.ConstraintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/constraints")
@RequiredArgsConstructor
public class ConstraintController {
    private final ConstraintService constraintService;

    //TODO: ВЫНЕСИ ЮЗЕР-АЙДИ ЛОГИКУ ПЛЫЗ
    private final static String X_USER_ID = "X-User-Id";

    @GetMapping("/{id}")
    public ResponseEntity<Constraint> getConstraint(@PathVariable Long id) {
        try {
            Constraint data = constraintService.getConstraint(id);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<Constraint> createConstraint(@RequestBody @Valid CreateConstraintRequest request, @RequestHeader(name = X_USER_ID) Long userId) {
        Constraint constraintData = Constraint.builder()
                .value(request.getValue())
                .timeToExpire(request.getTime())
                .id(request.getId())
                .timeCreated(LocalDateTime.now())
                .userId(userId).build();

        try {
            Constraint data = constraintService.createConstraint(constraintData);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Constraint> updateConstraint(@RequestBody @Valid UpdateConstraintRequest request, @PathVariable Long id) {
        try {
            Constraint item = constraintService.getConstraint(id);
            if(request.getTime() != null) {
                item.setTimeToExpire(request.getTime());
            }
            if(request.getValue() != null) {
                item.setValue(request.getValue());
            }

            Constraint data = constraintService.updateConstraint(item);

            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Constraint> deleteConstraint(@PathVariable Long id) {
        try {
            Constraint data = constraintService.getConstraint(id);

            if(constraintService.deleteConstraint(id)) {
                return ResponseEntity.ok(data);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Constraint>> getAllConstraints(@RequestHeader(name = X_USER_ID) Long userId) {
        try {
            List<Constraint> data = constraintService.getAllConstraints(userId);
            //Да я сталкивался с тем, что в лист первым элементом тупо нулл пихает. От греха подальше.
            if(data != null && data.get(0) != null) {
                return ResponseEntity.ok(data);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
}
