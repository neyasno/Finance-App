package org.example.authservice.services;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authservice.dto.ChangePasswordNotificationDTO;
import org.example.authservice.dto.UserCredentialsDTO;
import org.example.authservice.models.RecoverPasswordToken;
import org.example.authservice.repositories.RecoverPasswordTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountRecoveryService {

    @Value("${security.recovery.token-expiration-seconds}")
    private Long RECOVER_TOKEN_EXPIRES_IN_SECONDS;

    private final RecoverPasswordTokenRepository recoverPasswordTokenRepository;
    private final UserPasswordService userPasswordService;

    private final NotificationServiceClient notificationService;
    private final UserServiceClient userService;
//    private final MessageSenderService messageSenderService;

    public void sendRecoverPasswordEmailRequest(String email) {
        ResponseEntity<UserCredentialsDTO> userResponse = userService.getUserByEmail(email);

        UserCredentialsDTO user = validateUserResponse(userResponse);

        RecoverPasswordToken newToken = RecoverPasswordToken.builder()
                .email(email)
                .token(UUID.randomUUID().toString())
                .expirationDate(new Date(System.currentTimeMillis() + RECOVER_TOKEN_EXPIRES_IN_SECONDS * 1000))
                .build();

        newToken = recoverPasswordTokenRepository.save(newToken);

        notificationService.createPasswordChangeNotification(user.getId(), new ChangePasswordNotificationDTO(newToken.getToken()));
//        messageSenderService.sendMessage(user.getId(), newToken.getToken());
    }

    private static UserCredentialsDTO validateUserResponse(ResponseEntity<UserCredentialsDTO> userResponse) {
        if (!userResponse.getStatusCode().is2xxSuccessful()) {
            log.error("ERROR DURING RESPONSE TO USER SERVICE");
            throw new NotFoundException("User not found");
        }

        if (!userResponse.hasBody() || userResponse.getBody() == null) {
            log.error("ERROR DURING FETCHING USER DATA");
            throw new RuntimeException("User response body is empty");
        }

        return userResponse.getBody();
    }

    public void updatePasswordWithToken(String token, String newPassword) {
        RecoverPasswordToken recoverToken = recoverPasswordTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token not found"));

        ResponseEntity<UserCredentialsDTO> userResponse = userService.getUserByEmail(recoverToken.getEmail());

        UserCredentialsDTO user = validateUserResponse(userResponse);

        try {
            userPasswordService.UpdatePassword(newPassword, user.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
