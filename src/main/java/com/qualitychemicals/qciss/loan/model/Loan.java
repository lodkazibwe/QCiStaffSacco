package com.qualitychemicals.qciss.loan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qualitychemicals.qciss.transaction.dto.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Loan {
    @Id
    @GeneratedValue
    private int id;
    private String loanNumber;
    private String borrower;
    private double principal;
    private int duration;
    @Enumerated(EnumType.STRING)
    private  Cycle repaymentCycle;
    private RepaymentMode repaymentMode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date applicationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date releaseDate;
    @Enumerated(EnumType.STRING)
    private TransactionType disbursedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date firstRepaymentDate;

    private double handlingCharge;
    private double expressHandling;
    private double interest;
    private double insuranceFee;
    private double penalty;
    private double transferCharge;
    private double earlyTopUpCharge;
    private HandlingMode handlingMode;//express,normal
    private HandlingMode topUpMode;//express,normal

    @OneToOne(targetEntity = Security.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn()
    private Security security;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    @ManyToOne
    private Product product;
    @OneToMany(targetEntity = Repayment.class,cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinColumn
    private List<Repayment> repayments;
    private double totalPaid;
    private double totalDue;
    private String preparedBy;
    private String approvedBy;

    private int topUpLoanId;
    private double topUpLoanBalance;
    private String remarks;

}
