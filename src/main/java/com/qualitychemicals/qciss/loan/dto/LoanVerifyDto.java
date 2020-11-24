package com.qualitychemicals.qciss.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanVerifyDto {
    @NotNull
    private int id;
    @NotNull
    private double transferCharge;
    @Null
    private String remarks;
}
