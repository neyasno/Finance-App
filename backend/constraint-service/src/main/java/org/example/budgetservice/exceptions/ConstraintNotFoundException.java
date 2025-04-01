package org.example.budgetservice.exceptions;

public class ConstraintNotFoundException extends RuntimeException {
    public ConstraintNotFoundException(String message) {
        super(message);
    }
}