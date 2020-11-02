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
    private int penalty; //%age of principal
    private int interest;//%age of principal
    private int applicationFee;//%age of principal
    private double minAmount;
    private double maxAmount;
    private int minDuration;//months
    private int maxDuration;//months
    private String description;

}
