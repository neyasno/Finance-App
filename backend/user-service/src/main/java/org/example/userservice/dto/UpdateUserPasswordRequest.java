package org.example.userservice.dto;

import lombok.Data;

@Data
public class UpdateUserPasswordRequest {
    private String newPassword;
}
