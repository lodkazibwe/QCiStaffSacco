package com.qualitychemicals.qciss.saccoData.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ScheduleEmployee {
    @Id
    @GeneratedValue
    private int id;
    private int uid;
    private String firstName;
    private String lastName;
    private String mobile;
    private String employeeNumber;
    private double payrollSavings;
    private double payrollShares;
}
