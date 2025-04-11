package org.example.notificationservice.constants;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PasswordNotificationTemplate extends NotificationTemplate{
    private final String defaultTemplate = """
    Hello dear %s!
    There was an attempt to change the password for your account!

    If it is you, please click the following hyperlink: %s.

    On the other hand, please, contact the techical support to secure your account!
    """;
    private String userNameString = "defaultUserName";
    private String urlString = "defaultURL";
    public PasswordNotificationTemplate() {
    }

    public PasswordNotificationTemplate(String userName, String url) {
        this.userNameString = userName;
        this.urlString = url;
    }

    public String getNotificationString() {
        String result = String.format(defaultTemplate, this.userNameString, this.urlString);

        if(this.attachedLinks != null) {
            for(LinkAttachment attachment : attachedLinks) {
                result += "\n" + attachment.toString();
            }
        }

        return result;
    }
}
