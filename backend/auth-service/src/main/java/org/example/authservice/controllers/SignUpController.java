package org.example.authservice.controllers;


import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.CreateUserRequest;
import org.example.authservice.dto.MessageResponse;
import org.example.authservice.dto.UserCredentialsDTO;
import org.example.authservice.services.UserServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sign-up")
@RequiredArgsConstructor
public class SignUpController {
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/")
    public ResponseEntity<MessageResponse> registerUser(@RequestBody CreateUserRequest createUserRequest) {

        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        createUserRequest.setPassword(encodedPassword);

        ResponseEntity<UserCredentialsDTO> newUserData = userServiceClient.createUser(createUserRequest);

        if (newUserData.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(MessageResponse.fromMessage("Success"));
        }

        return ResponseEntity.badRequest().body(MessageResponse.fromMessage("Fail"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateUserPassword(@PathVariable Long id, @RequestBody String newPassword) {
        var encodedPassword = passwordEncoder.encode(newPassword);

        ResponseEntity<UserCredentialsDTO> newUserData = userServiceClient.updateUserPassword(id, encodedPassword);

        if (newUserData.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(MessageResponse.fromMessage("Success"));
        }
        return ResponseEntity.badRequest().body(MessageResponse.fromMessage("Fail"));
    }
}
