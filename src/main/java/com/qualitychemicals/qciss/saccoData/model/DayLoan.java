package com.qualitychemicals.qciss.saccoData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DayLoan extends DayAccount{
    private double interestReceivable;
    private double principalOutstanding;
    private double interestIn;
    private double principalOut;
    private double principalIn;
    private double handlingCharge;
    private double expressHandling;
    private double insuranceFee;
    private double transferCharge;
    private double earlyTopUpCharge;

}
