package com.qualitychemicals.qciss.profile.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkDTO {
    private int id;
    private String company;
    private String jobTittle;
    private String salaryScale;
    private int monthlySaving;
}
