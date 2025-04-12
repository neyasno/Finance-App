package org.example.authservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.RecoverPasswordByEmailRequest;
import org.example.authservice.dto.RecoverPasswordByTokenRequest;
import org.example.authservice.services.AccountRecoveryService;
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
    public void RecoverPasswordForEmail(@RequestBody @Valid RecoverPasswordByEmailRequest request) {
        accountRecoveryService.sendRecoverPasswordEmailRequest(request.getEmail());
    }

    @Operation(summary = "Confirm recovery and change password")
    @PostMapping("/confirmation")
    public void RecoverPasswordByToken(@RequestBody @Valid RecoverPasswordByTokenRequest request) {
        accountRecoveryService.updatePasswordWithToken(request.getToken(), request.getPassword());
    }

}
