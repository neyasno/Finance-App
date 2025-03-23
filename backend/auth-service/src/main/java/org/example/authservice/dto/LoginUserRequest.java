package org.example.authservice.dto;

import lombok.Data;

@Data
public class LoginUserRequest {
    private String email;
    private String password;
}
