package com.qualitychemicals.qciss.saccoData.dto;

import com.qualitychemicals.qciss.loan.model.RepaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleLoanDto {
    private int loanId;
    private String product;
    private String borrower;
    private double principal;
    private double interest;
    private double penalty;
    private double totalBalance;
    private double totalPaid;
    private double due;
    private double nextDue;
    private Date nextDueDate;
    private Date lastDueDate;
    private RepaymentMode repaymentMode;
}
