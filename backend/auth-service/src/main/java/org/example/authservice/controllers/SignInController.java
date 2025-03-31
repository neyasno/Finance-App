package org.example.authservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.LoginUserRequest;
import org.example.authservice.dto.LoginUserResponse;
import org.example.authservice.dto.UserCredentialsDTO;
import org.example.authservice.services.JwtService;
import org.example.authservice.services.UserServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class SignInController {
    private final JwtService jwtService;
    private final UserServiceClient userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<LoginUserResponse> loginUser(@RequestBody @Valid LoginUserRequest request) {

        ResponseEntity<UserCredentialsDTO> response = userService.getUserByEmail(request.getEmail());

        if (response.getStatusCode().is4xxClientError()) {
            return ResponseEntity.badRequest().build();
        }

        UserCredentialsDTO user = response.getBody();

        if(user == null) {
            return ResponseEntity.badRequest().build();
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().build();
        }

        String token = jwtService.generateToken(user.getId().toString());

        return ResponseEntity.ok(new LoginUserResponse(token));
    }
}