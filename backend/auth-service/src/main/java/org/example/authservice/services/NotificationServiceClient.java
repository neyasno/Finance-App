package org.example.authservice.services;

import org.example.authservice.dto.ChangePasswordNotificationDTO;
import org.example.authservice.dto.MessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static org.example.authservice.utils.CustomHeaders.X_USER_ID;

@FeignClient(name = "notification-service")
public interface NotificationServiceClient {

    @PostMapping("/password-change")
    ResponseEntity<MessageResponse> createPasswordChangeNotification(@RequestHeader(name = X_USER_ID) Long userId,
                                                                     @RequestBody ChangePasswordNotificationDTO token);
}
