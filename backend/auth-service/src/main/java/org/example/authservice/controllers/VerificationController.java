package org.example.authservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.UserIdDTO;
import org.example.authservice.services.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verification")
@RequiredArgsConstructor
@Schema(name = "Token Verification")
public class VerificationController {

    private final JwtService jwtService;

    @Operation(
            description = "Validates JWT token passed through `Authorization` header",
            responses = {
                    @ApiResponse(
                    )
            }
    )
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

