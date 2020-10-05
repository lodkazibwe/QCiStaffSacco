package com.qualitychemicals.qciss.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDto {
    private int id;
    private double totalSavings;
    private double totalShares;
    private double pendingFee;
}
