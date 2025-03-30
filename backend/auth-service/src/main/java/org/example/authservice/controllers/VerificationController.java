package org.example.authservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.UserIdDTO;
import org.example.authservice.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
public class VerificationController {

    private final JwtService jwtService;

    @GetMapping
    public ResponseEntity<UserIdDTO> validateToken(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");

        if (!jwtService.isValidToken(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String userId = jwtService.extractUserId(jwt);
        UserIdDTO userDetailsDto = new UserIdDTO(userId);

        return ResponseEntity.ok(userDetailsDto);
    }
}

