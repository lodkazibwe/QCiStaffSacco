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
    @NotNull
    private double totalSavings;
    @NotNull
    private double totalShares;
    @NotNull
    private double pendingFee;
}
