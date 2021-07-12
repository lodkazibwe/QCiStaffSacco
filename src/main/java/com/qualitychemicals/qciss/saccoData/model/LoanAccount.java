package com.qualitychemicals.qciss.saccoData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoanAccount extends SaccoAccount{

    private double interestReceivable; //totalInterestOutstanding
    private double amountIn;
    private double handlingCharge;
    private double expressHandling;
    private double insuranceFee;
    private double transferCharge;
    private double earlyTopUpCharge;
    private int count;
    //private int totalOutstandingLoans;

}
