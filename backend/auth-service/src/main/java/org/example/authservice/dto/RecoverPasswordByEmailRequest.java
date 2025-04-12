package org.example.authservice.dto;

import lombok.Data;

@Data
public class RecoverPasswordByEmailRequest {
    private String email;
}
