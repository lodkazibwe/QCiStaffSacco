package com.qualitychemicals.qciss.profile.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryDTO {
    private int id;
    private int totalSavings;
    private int totalShares;
    private int pendingFee;
}
