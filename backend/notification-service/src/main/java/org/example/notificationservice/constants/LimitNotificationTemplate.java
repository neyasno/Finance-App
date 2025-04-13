package org.example.notificationservice.constants;

import io.swagger.v3.oas.annotations.links.Link;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class LimitNotificationTemplate extends NotificationTemplate {
    private List<LinkAttachment> attachedLinks;
    private final String defaultTemplate = """
    Hello dear %s!
    Your constraint has been changed:
    %s
    """;

    public String userNameString = "defaultUserName";
    public String constraintString = "defaultConstraintInfo";
    public LimitNotificationTemplate() {
    }

    public LimitNotificationTemplate(String userName, String constraintInfo) {
        this.userNameString = userName;
        this.constraintString = constraintInfo;
    }

    public LimitNotificationTemplate(String userName, String constraintInfo, List<LinkAttachment> attachments) {
        this.userNameString = userName;
        this.constraintString = constraintInfo;
        this.attachedLinks = attachments;
    }

    public String getNotificationString() {
        String result = String.format(defaultTemplate, this.userNameString, this.constraintString);

        if(this.attachedLinks != null) {
            for(LinkAttachment attachment : attachedLinks) {
                result += "\n" + attachment.toString();
            }
        }

        return result;
    }

}
