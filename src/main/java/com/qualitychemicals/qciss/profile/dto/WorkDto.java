package com.qualitychemicals.qciss.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDto {
    private int id;
    private String company;
    private String jobTittle;
    private String salaryScale;
    private double monthlySaving;
}
