package org.example.authservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
