package org.example.notificationservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.example.notificationservice.clients.UserServiceClient;
import org.example.notificationservice.constants.Constants;
import org.example.notificationservice.dto.ConstraintDTO;
import org.example.notificationservice.dto.MessageResponse;
import org.example.notificationservice.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final JavaMailSender mailSender;
    private final static String X_USER_ID = "X-User-Id";
    private final NotificationService notificationService;
    @PostMapping("/password-change")
    public ResponseEntity<MessageResponse> createPasswordChangeNotification(@RequestHeader(name = X_USER_ID) Long userId) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("OUR EMAIL");
            message.setTo(notificationService.getUserEmail(userId));
            message.setSubject(Constants.PASSWORD_NOTIFICATION_HEADER);
            message.setText(notificationService.constructPasswordNotification(userId, Constants.ESTABLISHED_URLS));

            mailSender.send(message);
            return ResponseEntity.ok(MessageResponse.fromMessage("Email sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/budget-limit")
    public ResponseEntity<MessageResponse> createLimitNotification(@RequestHeader(name = X_USER_ID) Long userId,
                                                                   @RequestBody @Valid ConstraintDTO constraint) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("OUR EMAIL");
                message.setTo(notificationService.getUserEmail(userId));
                message.setSubject(Constants.PASSWORD_NOTIFICATION_HEADER);
                message.setText(
                        notificationService.constructLimitNotification(userId,
                        constraint.toString(),
                        Constants.ESTABLISHED_URLS));

                mailSender.send(message);
                return ResponseEntity.ok(MessageResponse.fromMessage("Email sent successfully"));
            } catch (Exception e) {
                return ResponseEntity.badRequest().build();
            }
    }
}
