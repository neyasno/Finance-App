package org.example.notificationservice.constants;

public class Constants {
    public Constants(){

    }

    public String getPasswordNotificationString() {
        PasswordNotificationTemplate template = new PasswordNotificationTemplate();
        return template.getNotificationString();
    }

    public String getPasswordNotificationString(String username, String url) {
        PasswordNotificationTemplate template = new PasswordNotificationTemplate(username, url);
        return template.getNotificationString();
    }

    public String getPasswordNotificationString(String username, String url, LinkAttachment... attachments) {
        PasswordNotificationTemplate template = new PasswordNotificationTemplate(username, url);

        for(LinkAttachment attachment: attachments) {
            template.addAttachedLink( attachment);
        }

        return template.getNotificationString();
    }

    public String getLimitNotificationString() {
        LimitNotificationTemplate template = new LimitNotificationTemplate();
        return template.getNotificationString();
    }

    public String getLimitNotificationString(String username, String constraintInfo) {
        LimitNotificationTemplate template = new LimitNotificationTemplate(username, constraintInfo);
        return template.getNotificationString();
    }

    public String getLimitNotificationString(String username, String constraintInfo, LinkAttachment... attachments) {
        LimitNotificationTemplate template = new LimitNotificationTemplate(username, constraintInfo);

        for(LinkAttachment attachment: attachments) {
            template.addAttachedLink( attachment);
        }

        return template.getNotificationString();
    }
}
