package org.example.authservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.MessageResponse;
import org.example.authservice.dto.UserCredentialsDTO;
import org.example.authservice.services.UserServiceClient;
import org.example.authservice.utils.CustomHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PasswordChangeController {
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;

    @PutMapping("/password_change")
    public ResponseEntity<MessageResponse> updateUserPassword(@RequestBody String newPassword, @RequestHeader(CustomHeaders.X_USER_ID) Long userId) {
        var encodedPassword = passwordEncoder.encode(newPassword);

        ResponseEntity<UserCredentialsDTO> newUserData = userServiceClient.updateUserPassword(userId, encodedPassword);

        if (newUserData.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(MessageResponse.fromMessage("Success"));
        }
        return ResponseEntity.badRequest().body(MessageResponse.fromMessage("Fail"));
    }
}
