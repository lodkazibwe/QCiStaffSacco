package com.qualitychemicals.qciss.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {
    private int id;
    private String user;
    private String name;
    private String accountRef;
    private double amount;
    private int lastTransaction;

    private String contact;
}
