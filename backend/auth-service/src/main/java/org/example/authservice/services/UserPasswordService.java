package org.example.authservice.services;

import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.UpdateUserPasswordRequest;
import org.example.authservice.dto.UserCredentialsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPasswordService {

    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;

    public void UpdatePassword(String newPassword, Long userId) {

        var encodedPassword = passwordEncoder.encode(newPassword);

        ResponseEntity<UserCredentialsDTO> newUserData = userServiceClient.updateUserPassword(userId, new UpdateUserPasswordRequest(encodedPassword));

        if(!newUserData.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Error updating password");
        }
    }
}
