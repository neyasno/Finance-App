package org.example.authservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authservice.dto.CreateUserRequest;
import org.example.authservice.dto.MessageResponse;
import org.example.authservice.dto.UserCredentialsDTO;
import org.example.authservice.services.UserServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class SignUpController {
    private final UserServiceClient userServiceClient;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<MessageResponse> registerUser(@RequestBody CreateUserRequest createUserRequest) {

        String encodedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        createUserRequest.setPassword(encodedPassword);

        ResponseEntity<UserCredentialsDTO> newUserData;

        try {
            newUserData = userServiceClient.createUser(createUserRequest);
        }catch (Exception e) {
            log.error("USER_SERVICE_CLIENT_ERROR: {}",e.getMessage());
            return ResponseEntity.badRequest().body(MessageResponse.fromMessage(e.getMessage()));
        }

        log.info("USER_DATA: {}", newUserData.toString());

        if (newUserData.getStatusCode().is2xxSuccessful()) {
            log.info("User registered successfully");
            return ResponseEntity.ok(MessageResponse.fromMessage("Success"));
        }

        log.info("User registration failed");
        return ResponseEntity.badRequest().body(MessageResponse.fromMessage("Fail"));
    }
}
