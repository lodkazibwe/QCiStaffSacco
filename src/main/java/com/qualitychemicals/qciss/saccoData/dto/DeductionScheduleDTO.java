package com.qualitychemicals.qciss.saccoData.dto;

import com.qualitychemicals.qciss.saccoData.model.ScheduleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DeductionScheduleDTO {
    private int id;
    private ScheduleStatus status;
    private ScheduleEmployeeDto employee;
    private List<ScheduleLoanDto> dueLoans;
    private double total;
}
