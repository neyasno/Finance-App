package org.example.authservice.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.ChangePasswordRequest;
import org.example.authservice.dto.MessageResponse;
import org.example.authservice.dto.UpdateUserPasswordRequest;
import org.example.authservice.dto.UserCredentialsDTO;
import org.example.authservice.services.UserPasswordService;
import org.example.authservice.services.UserServiceClient;
import org.example.authservice.utils.CustomHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PasswordChangeController {


    private final UserPasswordService userPasswordService;

    @PutMapping("/password-change")
    public ResponseEntity<MessageResponse> updateUserPassword(
            @RequestBody @Valid ChangePasswordRequest request,
            @RequestHeader(CustomHeaders.X_USER_ID) Long userId
    ) {
        try {
            userPasswordService.UpdatePassword(request.getPassword(), userId);

            return ResponseEntity.ok(MessageResponse.fromMessage("Success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(MessageResponse.fromMessage("Fail"));
        }
    }
}
