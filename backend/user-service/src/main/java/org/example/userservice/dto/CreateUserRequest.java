package org.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
public class CreateUserRequest {
    @NotBlank
    @Email
    private String email;

    @NotNull
    private String password;
}