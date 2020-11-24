package com.qualitychemicals.qciss.profile.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDto {
    private int id;
    private String name;
    private String branch;
    private String accountName;
    private String accountNumber;
}
