package org.example.authservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.RecoverPasswordByEmailRequest;
import org.example.authservice.dto.RecoverPasswordByTokenRequest;
import org.example.authservice.services.AccountRecoveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recovery")
@RequiredArgsConstructor
public class AccountRecoveryController {

    private final AccountRecoveryService accountRecoveryService;

    @Operation(summary = "Send email message for recovery")
    @PostMapping("/request")
    public ResponseEntity<Void> RecoverPasswordForEmail(@RequestBody @Valid RecoverPasswordByEmailRequest request) {
        accountRecoveryService.sendRecoverPasswordEmailRequest(request.getEmail());

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Confirm recovery and change password")
    @PostMapping("/confirmation")
    public ResponseEntity<Void> RecoverPasswordByToken(@RequestBody @Valid RecoverPasswordByTokenRequest request) {
        accountRecoveryService.updatePasswordWithToken(request.getToken(), request.getPassword());

        return ResponseEntity.noContent().build();
    }

}
