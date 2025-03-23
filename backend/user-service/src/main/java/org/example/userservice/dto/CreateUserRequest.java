package org.example.userservice.dto;

import lombok.*;

@Data
public class CreateUserRequest {
    private String email;
    private String password;
}