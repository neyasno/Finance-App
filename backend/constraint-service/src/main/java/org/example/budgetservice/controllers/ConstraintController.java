package org.example.budgetservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.budgetservice.dto.CreateConstraintRequest;
import org.example.budgetservice.dto.UpdateConstraintRequest;
import org.example.budgetservice.models.Constraint;
import org.example.budgetservice.services.ConstraintService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/constraints")
@RequiredArgsConstructor
public class ConstraintController {
    private final ConstraintService constraintService;
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
    public ResponseEntity<Constraint> createConstraint(@RequestBody CreateConstraintRequest request) {
        Constraint constraintData = Constraint.builder()
                .value(request.getValue())
                .time(request.getTime())
                .id(request.getId()).build();

        try {
            Constraint data = constraintService.createConstraint(constraintData);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Constraint> updateConstraint(@RequestBody UpdateConstraintRequest request, @PathVariable Long id) {
        try {
            Constraint item = constraintService.getConstraint(id);
            if(request.getTime() != null) {
                item.setTime(request.getTime());
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
}
