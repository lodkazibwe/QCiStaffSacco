package com.qualitychemicals.qciss.firebase.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class SmsService {
     private final Logger logger = LoggerFactory.getLogger(SmsService.class);

    public static final String ACCOUNT_SID = "AC9a2b8077acf2b50e4f3b887b1d06e18a";
    public static final String AUTH_TOKEN = "a06768969e255c91a7ae13a1c80ffe79";

    public void sendSms(String number, String sms) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message =Message.creator(new com.twilio.type.PhoneNumber("+256"+number),
                "MG2bff562b0ca5896d9dcb5d12e0e5d096",sms).create();
        logger.info("message sent... "+sms);
    }

}
