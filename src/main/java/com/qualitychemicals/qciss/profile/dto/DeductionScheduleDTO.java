package com.qualitychemicals.qciss.profile.dto;

import com.qualitychemicals.qciss.loan.dto.DueLoanDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DeductionScheduleDTO {
    private EmployeeDto employee;
    private List<DueLoanDto> dueLoans;
    private double total;
}
