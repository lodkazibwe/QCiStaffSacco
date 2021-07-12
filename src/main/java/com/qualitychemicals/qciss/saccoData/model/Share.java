package com.qualitychemicals.qciss.saccoData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Share extends SaccoAccount{

    private double totalShares;
    private double reservedShares;
    private double sharesSold;
    private double sharesAvailable;
    private double shareValue;
    private Date recordDate;
    private double dividendsPerShare;
}
