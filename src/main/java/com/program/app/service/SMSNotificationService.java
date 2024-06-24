package com.program.app.service;

import org.springframework.stereotype.Service;

@Service
public class SMSNotificationService implements NotificationService{
    @Override
    public void notifyCreation(String notification) {
        System.out.println("SMS created:" + notification);
    }

    @Override
    public void notifyCompletion(String notification) {
        System.out.println("SMS is sent: " + notification);
    }
}
