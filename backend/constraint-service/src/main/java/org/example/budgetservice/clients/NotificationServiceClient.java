package org.example.budgetservice.clients;

import jakarta.validation.Valid;
import org.example.budgetservice.dto.MessageResponse;
import org.example.budgetservice.models.Constraint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {

    @PostMapping("/budget-limit")
    ResponseEntity<MessageResponse> createLimitNotification(@RequestHeader("X-User-Id") Long userId,
                                                            @RequestBody @Valid Constraint constraint);
}
