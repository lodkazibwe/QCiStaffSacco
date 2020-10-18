package com.qualitychemicals.qciss.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeDto {
    private double interest;
    private double penalty;
    private double applicationFee;
}
