package com.qualitychemicals.qciss.loan.dto;

import com.qualitychemicals.qciss.loan.model.RepaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanSummaryDto {
    private int loanId;
    private String productName;
    private String borrower;
    private double totalPaid;
    private double totalDue;
    private double nextDue;
    private Date nextDueDate;
    private double penalty;
    private RepaymentMode repaymentMode;

}
