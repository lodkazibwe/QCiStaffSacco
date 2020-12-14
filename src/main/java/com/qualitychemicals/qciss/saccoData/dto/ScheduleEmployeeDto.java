package com.qualitychemicals.qciss.saccoData.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEmployeeDto {
    private int uid;
    private String firstName;
    private String lastName;
    private String mobile;
    private String employeeNumber;
    private double payrollSavings;
    private double payrollShares;
}
