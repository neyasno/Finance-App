package org.example.notificationservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notificationservice.clients.UserServiceClient;
import org.example.notificationservice.constants.LinkAttachment;
import org.example.notificationservice.constants.TemplateHelper;
import org.example.notificationservice.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserServiceClient userServiceClient;

    String url = "url";

    public String getUserEmail(Long userId) {
        return userServiceClient.getUserById(userId).getBody().getEmail();
    }

    public String constructPasswordNotification(){
        return TemplateHelper.getPasswordNotificationString();
    }

    public String constructPasswordNotification(Long userId) {

        UserDTO data = userServiceClient.getUserById(userId).getBody();
        return TemplateHelper.getPasswordNotificationString(data.getName(), url);
    }

    public String constructPasswordNotification(Long userId, List<LinkAttachment> attachments) {

        UserDTO data = userServiceClient.getUserById(userId).getBody();
        return TemplateHelper.getPasswordNotificationString(data.getName(), url, attachments);
    }

    public String constructPasswordNotification(Long userId, String tokenURL, List<LinkAttachment> attachments) {

        UserDTO data = userServiceClient.getUserById(userId).getBody();
        return TemplateHelper.getPasswordNotificationString(data.getName(), tokenURL, attachments);
    }

    public String constructLimitNotification() {
        return TemplateHelper.getLimitNotificationString();
    }

    public String constructLimitNotification(Long userId, String constraintInfo) {
        UserDTO data = userServiceClient.getUserById(userId).getBody();
        return TemplateHelper.getLimitNotificationString(data.getName(), constraintInfo);
    }

    public String constructLimitNotification(Long userId, String constraintInfo, List<LinkAttachment> attachments) {
        UserDTO data = userServiceClient.getUserById(userId).getBody();
        return TemplateHelper.getLimitNotificationString(data.getName(), constraintInfo, attachments);
    }

}
