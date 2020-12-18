package com.qualitychemicals.qciss.firebase.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String text;
    private Subject subject;
    private String sentTo;
    private Date date;
    private NotificationStatus status;
}
