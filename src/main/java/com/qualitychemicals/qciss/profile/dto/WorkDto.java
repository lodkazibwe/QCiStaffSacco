package com.qualitychemicals.qciss.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDto {
    private int id;
    private String companyName;
    private String jobTittle;
    private String salaryScale;
    @NotNull
    private double monthlySaving;
}
