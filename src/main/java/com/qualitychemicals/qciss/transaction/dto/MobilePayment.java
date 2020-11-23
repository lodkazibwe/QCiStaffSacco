package com.qualitychemicals.qciss.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobilePayment {
    private String to;
    private String from;
    private double amount;
}
