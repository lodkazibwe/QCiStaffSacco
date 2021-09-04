package com.qualitychemicals.qciss.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private int id;
    @NotNull(message = "totalSavings can not be null")
    private double totalSavings;
    @NotNull(message = "totalShares can not be null")
    private double totalShares;
    @NotNull(message = "pendingFee can not be null")
    private double pendingFee;
    private double walletAmount;
    @NotNull(message = "memberNo can not be null")
    private String memberNo;
    @NotNull(message = "position can not be null")
    private String position;

}
