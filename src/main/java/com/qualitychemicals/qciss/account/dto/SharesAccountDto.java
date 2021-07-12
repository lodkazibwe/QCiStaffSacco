package com.qualitychemicals.qciss.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharesAccountDto {
    private int id;
    private String user;
    private String name;
    private String accountRef;
    private double amount;
    private int lastTransaction;

    private double shares;
    private double recordShares;
    private double dividend;
}
