package com.qualitychemicals.qciss.saccoData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Membership extends SaccoAccount{

    private double membershipFee;

    /************reset daily*****************/
    private double dayMembership;

}
