package com.qualitychemicals.qciss.account.model;

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
public class SharesAccount extends UserAccount {

    private double shares;
    private double recordShares;
    private double dividend;

}
