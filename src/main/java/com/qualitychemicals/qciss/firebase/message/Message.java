package com.qualitychemicals.qciss.firebase.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String text;
    private String sentBy;
    private Date sentAt;
    private String status;
}
