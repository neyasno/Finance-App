package org.example.notificationservice.constants;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String TOKEN_URL_TEMPLATE = "http://localhost:8080/api/auth/recovery/request?token=%s";
    public static final String PASSWORD_NOTIFICATION_HEADER = "Password change";
    public static final List<LinkAttachment> ESTABLISHED_URLS = Arrays.asList(
            new LinkAttachment("Visit out site: ", "SITE-URL"),
            new LinkAttachment("Technical support: ", "SUPPORT-URL"));
    private Constants(){

    }
}
