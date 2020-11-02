package com.qualitychemicals.qciss.profile.service;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}
