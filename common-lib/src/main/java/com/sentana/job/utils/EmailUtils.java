package com.sentana.job.utils;

import org.springframework.beans.factory.annotation.Value;

public class EmailUtils {
    @Value("${server.servlet.context-path}")
    static String apiVersion;
    public static String getVerificationUrl(String host, String token) {
        return host + apiVersion + "/user-account/verify-email?token=" + token;
    }
    public static String getEmailMessage(String name, String host, String token) {
        return "Hello " + name + "\n\nYour new account has been created. please click the link below to verify your account." + "\n\n" + getVerificationUrl(host, token);
    }
}
