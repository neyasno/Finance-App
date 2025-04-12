package org.example.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RecoverPasswordByTokenRequest {
    @Schema(description = "This token must be attached to the email")
    private String token;

    @Schema(description = "New password")
    private String password;
}