package com.qualitychemicals.qciss.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanRejectDto {
    private int id;
    private String reason;
}
