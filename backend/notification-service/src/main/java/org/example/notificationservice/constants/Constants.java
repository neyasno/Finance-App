package org.example.notificationservice.constants;

import java.util.Arrays;
import java.util.List;

public class Constants {
    //TODO: ЗДЕСЬ НУЖНО ОПРЕДЕЛИТЬ НАШ ЭМЕЙЛ
    public static final String APP_EMAIL = "EMAIL";
    public static final String PASSWORD_NOTIFICATION_HEADER = "Password change";
    public static final List<LinkAttachment> ESTABLISHED_URLS = Arrays.asList(
            new LinkAttachment("Visit out site: ", "SITE-URL"),
            new LinkAttachment("Technical support: ", "SUPPORT-URL"));
    private Constants(){

    }

    public static String getPasswordNotificationString() {
        PasswordNotificationTemplate template = new PasswordNotificationTemplate();
        return template.getNotificationString();
    }

    public static String getPasswordNotificationString(String username, String url) {
        PasswordNotificationTemplate template = new PasswordNotificationTemplate(username, url);
        return template.getNotificationString();
    }

    public static String getPasswordNotificationString(String username, String url, List<LinkAttachment> attachments) {
        PasswordNotificationTemplate template = new PasswordNotificationTemplate(username, url);

        for(LinkAttachment attachment: attachments) {
            template.addAttachedLink( attachment);
        }

        return template.getNotificationString();
    }

    public static String getLimitNotificationString() {
        LimitNotificationTemplate template = new LimitNotificationTemplate();
        return template.getNotificationString();
    }

    public static String getLimitNotificationString(String username, String constraintInfo) {
        LimitNotificationTemplate template = new LimitNotificationTemplate(username, constraintInfo);
        return template.getNotificationString();
    }

    public static String getLimitNotificationString(String username, String constraintInfo, List<LinkAttachment> attachments) {
        LimitNotificationTemplate template = new LimitNotificationTemplate(username, constraintInfo);

        for(LinkAttachment attachment: attachments) {
            template.addAttachedLink( attachment);
        }

        return template.getNotificationString();
    }
}
