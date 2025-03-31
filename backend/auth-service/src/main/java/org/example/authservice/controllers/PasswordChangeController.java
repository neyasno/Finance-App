package org.example.authservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.MessageResponse;
import org.example.authservice.dto.UserCredentialsDTO;
import org.example.authservice.services.UserServiceClient;
import org.example.authservice.utils.CustomHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PasswordChangeController {
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.min-password-length}")
    private Integer minPasswordLength;

    @Value("${security.max-password-length}")
    private Integer maxPasswordLength;

    @PutMapping("/password_change")
    public ResponseEntity<MessageResponse> updateUserPassword(@RequestBody String newPassword, @RequestHeader(CustomHeaders.X_USER_ID) Long userId) {
        if(newPassword == null || newPassword.length() < minPasswordLength || newPassword.length() > maxPasswordLength) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponse.fromMessage("Invalid password"));
        }

        var encodedPassword = passwordEncoder.encode(newPassword);

        ResponseEntity<UserCredentialsDTO> newUserData = userServiceClient.updateUserPassword(userId, encodedPassword);

        if (newUserData.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(MessageResponse.fromMessage("Success"));
        }
        return ResponseEntity.badRequest().body(MessageResponse.fromMessage("Fail"));
    }
}
