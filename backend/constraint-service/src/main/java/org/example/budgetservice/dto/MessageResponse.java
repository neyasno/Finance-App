package org.example.budgetservice.dto;

import lombok.Getter;

@Getter
public final class MessageResponse {
    private final String message;

    private MessageResponse(String message) {
        this.message = message;
    }

    public static MessageResponse fromMessage(String message) {
        return new MessageResponse(message);
    }
}