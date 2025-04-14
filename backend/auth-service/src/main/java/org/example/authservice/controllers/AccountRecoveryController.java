package org.example.authservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authservice.dto.MessageResponse;
import org.example.authservice.dto.RecoverPasswordByEmailRequest;
import org.example.authservice.dto.RecoverPasswordByTokenRequest;
import org.example.authservice.services.AccountRecoveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/recovery")
@RequiredArgsConstructor
public class AccountRecoveryController {

    private final AccountRecoveryService accountRecoveryService;

    @Operation(summary = "Send email message for recovery")
    @PostMapping("/request")
    public ResponseEntity<MessageResponse> RecoverPasswordForEmail(@RequestBody @Valid RecoverPasswordByEmailRequest request) {
        try {
            accountRecoveryService.sendRecoverPasswordEmailRequest(request.getEmail());
            return ResponseEntity.ok(MessageResponse.fromMessage("success"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(MessageResponse.fromMessage(e.getMessage()));
        }
    }

    @Operation(summary = "Confirm recovery and change password")
    @PostMapping("/confirmation")
    public ResponseEntity<MessageResponse> RecoverPasswordByToken(@RequestBody @Valid RecoverPasswordByTokenRequest request) {
        try {
            accountRecoveryService.updatePasswordWithToken(request.getToken(), request.getPassword());
            return ResponseEntity.ok(MessageResponse.fromMessage("success"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(MessageResponse.fromMessage(e.getMessage()));
        }
    }
}
