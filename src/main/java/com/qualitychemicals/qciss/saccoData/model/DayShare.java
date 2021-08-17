package com.qualitychemicals.qciss.saccoData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DayShare extends DayAccount{
    private double sharesSold;
    private double sharesAvailable;
    private double shareValue;
    private double dayShares;
    private double dayShareAmount;
}
