package com.qualitychemicals.qciss.loan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Repayment {
    @Id
    @GeneratedValue
    private int id;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date date;
    private double amount;
    private double paid;
    private double balance;
    private double principal;
    private double interest;
    private double principalPaid;
    private double interestPaid;
    @Enumerated(EnumType.STRING)
    private RepaymentStatus status;//pending,settled,pastDue

}
