package com.qualitychemicals.qciss.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private int uid;
    private String firstName;
    private String lastName;
    private String mobile;
    private String employeeNumber;
    private double payrollSavings;
    private double payrollShares;

}
