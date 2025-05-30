package org.example.notificationservice.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.constants.Constants;
import org.example.notificationservice.constants.TemplateHelper;
import org.example.notificationservice.dto.ConstraintDTO;
import org.example.notificationservice.dto.MessageResponse;
import org.example.notificationservice.dto.PasswordTokenDTO;
import org.example.notificationservice.services.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NotificationController {
    @Value("${spring.mail.username}")
    private String APP_EMAIL;
    private final JavaMailSender mailSender;
    private final static String X_USER_ID = "X-User-Id";
    private final NotificationService notificationService;


    @PostMapping("/password-change")
    public ResponseEntity<MessageResponse> createPasswordChangeNotification(@RequestHeader(name = X_USER_ID) Long userId,
                                                                            @RequestBody @Valid PasswordTokenDTO token) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(this.APP_EMAIL);
            message.setTo(notificationService.getUserEmail(userId));
            message.setSubject(Constants.PASSWORD_NOTIFICATION_HEADER);
            message.setText(notificationService.constructPasswordNotification(
                    userId,
                    TemplateHelper.getLinkFromToken(token.getToken()),
                    Constants.ESTABLISHED_URLS));

            mailSender.send(message);
            return ResponseEntity.ok(MessageResponse.fromMessage("Email sent successfully"));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/budget-limit")
    public ResponseEntity<MessageResponse> createLimitNotification(@RequestHeader(name = X_USER_ID) Long userId,
                                                                   @RequestBody @Valid ConstraintDTO constraint) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(this.APP_EMAIL);
                message.setTo(notificationService.getUserEmail(userId));
                message.setSubject(Constants.LIMIT_NOTIFICATION_HEADER);
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
