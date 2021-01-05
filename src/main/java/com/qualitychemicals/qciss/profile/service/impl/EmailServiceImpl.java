package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.profile.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl  implements EmailService {

    @Autowired JavaMailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        logger.info("composing email...");
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        //mailMessage.setFrom("app@qcstaffsacco.com");
        logger.info("sending email...");
        mailSender.send(mailMessage);
    }
}
