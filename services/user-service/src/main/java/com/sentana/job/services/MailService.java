package com.sentana.job.services;

public interface MailService {
    void sendEmailMessage(String name, String to, String token);

    void sendHtmlEmailMessage(String name, String to, String token);
}
