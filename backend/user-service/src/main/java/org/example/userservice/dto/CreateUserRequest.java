package org.example.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class CreateUserRequest {
    @NotBlank
    @Email
    private String email;

    @NotNull
    @Size(min = 6, max = 32)
    private String password;
}