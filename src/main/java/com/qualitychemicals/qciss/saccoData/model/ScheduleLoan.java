package com.qualitychemicals.qciss.saccoData.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qualitychemicals.qciss.loan.model.RepaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ScheduleLoan {
    @Id
    @GeneratedValue
    private int id;
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
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date nextDueDate;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date lastDueDate;
    private RepaymentMode repaymentMode;
}
