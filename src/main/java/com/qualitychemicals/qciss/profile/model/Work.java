package com.qualitychemicals.qciss.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Work {
    @Id
    @GeneratedValue
    private int id;
    private String companyName;
    private String employeeId;
    @Enumerated(EnumType.STRING)
    private TOE toe;
    private String workStation;
    private double basicSalary;
    private String job;
    private double payrollSaving;
    private double payrollShares;
}
