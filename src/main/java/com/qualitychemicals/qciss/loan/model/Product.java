package com.qualitychemicals.qciss.loan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue
    private  int id;
    @Column(unique=true)
    private String name;
    @Column(unique=true)
    private String productNumber;
    private double penalty; //%age of principal
    private double interest;//%age of principal
    private double handlingCharge;//%age of principal
    private double expressHandling;
    private double insuranceRate;//%age of principal
    private double earlyTopUp;
    private double minAmount;
    private double maxAmount;
    private double maximum;//percentage of saving 200%
    private int minDuration;//months
    private int maxDuration;//months
    private String description;

}
