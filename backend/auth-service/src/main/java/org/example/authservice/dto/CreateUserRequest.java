package org.example.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Create User Request")
public class CreateUserRequest {
    @NotBlank
    @Email
    @Schema(defaultValue = "test@mail.ru")
    private String email;

    @NotNull
    @Size(min = 6, max = 32)
    @Schema(defaultValue = "123123")
    private String password;
}
