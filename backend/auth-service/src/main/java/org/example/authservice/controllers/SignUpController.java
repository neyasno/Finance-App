package org.example.authservice.controllers;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authservice.dto.CreateUserRequest;
import org.example.authservice.dto.MessageResponse;
import org.example.authservice.dto.UserCredentialsDTO;
import org.example.authservice.services.UserServiceClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

        try {
            ResponseEntity<UserCredentialsDTO> newUserData = userServiceClient.createUser(createUserRequest);

            if (newUserData.getStatusCode().is2xxSuccessful()) {
                log.info("User registered successfully");
                return ResponseEntity.ok(MessageResponse.fromMessage("Success"));
            }
        } catch (FeignException e) {
            log.error("USER_SERVICE_ERROR: {}", e.getMessage());
            return ResponseEntity.status(e.status()).body(MessageResponse.fromMessage("Error"));
        }

        log.info("User registration failed");
        return ResponseEntity.badRequest().body(MessageResponse.fromMessage("Fail"));
    }
}
