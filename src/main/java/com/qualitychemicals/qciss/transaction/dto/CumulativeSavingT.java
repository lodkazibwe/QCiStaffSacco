package com.qualitychemicals.qciss.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CumulativeSavingT {
    private Date creationDateTime;
    private double amount;
    private String account;
    private String narrative;
    private double cumulativeAmount;
}
