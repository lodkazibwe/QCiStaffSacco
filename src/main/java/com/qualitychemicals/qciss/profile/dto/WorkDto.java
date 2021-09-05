package com.qualitychemicals.qciss.profile.dto;

import com.qualitychemicals.qciss.profile.model.TOE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDto {
    private int id;
    @NotNull(message = "company name cannot be null...")
    private String companyName;
    @NotNull(message = "employeeId name cannot be null...")
    private String employeeId;
    @NotNull(message = "terms of employment name cannot be null...")
    private TOE toe;
    @NotNull(message = "workStation name cannot be null...")
    private String workStation;
    @NotNull(message = "basicSalary cannot be null...")
    private double basicSalary;
    @NotNull(message = "job cannot be null...")
    private String job;
    //@NotNull(message = "payrollSaving cannot be null...")
    private double payrollSaving;
    //@NotNull(message = "payrollShares cannot be null...")
    private double payrollShares;
}
