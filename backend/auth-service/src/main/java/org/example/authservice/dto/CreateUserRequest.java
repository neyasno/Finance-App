package org.example.authservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Create User Request")
public class CreateUserRequest {
    @Schema(defaultValue = "test@mail.ru")
    private String email;

    @Schema(defaultValue = "123")
    private String password;
}
