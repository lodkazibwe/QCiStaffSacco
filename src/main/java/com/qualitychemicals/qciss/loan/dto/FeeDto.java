package com.qualitychemicals.qciss.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeDto {
    private double interest;
    private double possiblePenalty;
    private double handlingCharge;
    private double expressHandling;
    private double insuranceFee;
    private double earlyTopUpCharge;
}
