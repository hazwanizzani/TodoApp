package com.program.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService{


    @Value("${notification.email.from}")
    private String fromEmail;

    @Override
    public void notifyCreation(String notification) {
        System.out.println("Email is being sent: " + notification);
    }

    @Override
    public void notifyCompletion(String notification) {
        System.out.println("Email is sent: " + notification);
    }
}
