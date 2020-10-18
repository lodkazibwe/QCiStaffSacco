package com.qualitychemicals.qciss.loan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qualitychemicals.qciss.transaction.model.TransactionType;
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
    private double principal;
    private int duration;
    @Enumerated(EnumType.STRING)
    private  Cycle repaymentCycle;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date applicationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date releaseDate;
    @Enumerated(EnumType.STRING)
    private TransactionType disbursedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date firstRepaymentDate;
    private double applicationFee;
    private double interest;
    private double penalty;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    @OneToOne(targetEntity = Product.class, fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn
    private Product product;
    @OneToMany(targetEntity = Repayment.class,cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    @JoinColumn
    private List<Repayment> repayments;
    private double totalPaid;
    private double totalDue;
    private String borrower;
    private String comment;
    private String approvedBy;

}
