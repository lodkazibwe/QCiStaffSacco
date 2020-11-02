package com.qualitychemicals.qciss.loan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DueLoanDto {
    private int loanId;
    private String borrower;
    private double principal;
    private double interest;
    private double penalty;
    private double totalBalance;
    private double totalPaid;
    private double due;

}
