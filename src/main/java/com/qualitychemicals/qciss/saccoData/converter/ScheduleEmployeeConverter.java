package com.qualitychemicals.qciss.saccoData.converter;

import com.qualitychemicals.qciss.saccoData.dto.ScheduleEmployeeDto;
import com.qualitychemicals.qciss.saccoData.model.ScheduleEmployee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleEmployeeConverter {
    public ScheduleEmployeeDto entityToDto(ScheduleEmployee scheduleEmployee){
        ScheduleEmployeeDto scheduleEmployeeDto =new ScheduleEmployeeDto();
        scheduleEmployeeDto.setLastName(scheduleEmployee.getLastName());
        scheduleEmployeeDto.setEmployeeNumber(scheduleEmployee.getEmployeeNumber());
        scheduleEmployeeDto.setFirstName(scheduleEmployee.getFirstName());
        scheduleEmployeeDto.setMobile(scheduleEmployee.getMobile());
        scheduleEmployeeDto.setPayrollSavings(scheduleEmployee.getPayrollSavings());
        scheduleEmployeeDto.setPayrollShares(scheduleEmployee.getPayrollShares());
        scheduleEmployeeDto.setUid(scheduleEmployee.getUid());
        return scheduleEmployeeDto;
    }

    public ScheduleEmployee dtoToEntity(ScheduleEmployeeDto scheduleEmployeeDto){
        ScheduleEmployee scheduleEmployee =new ScheduleEmployee();
        scheduleEmployee.setLastName(scheduleEmployeeDto.getLastName());
        scheduleEmployee.setEmployeeNumber(scheduleEmployeeDto.getEmployeeNumber());
        scheduleEmployee.setFirstName(scheduleEmployeeDto.getFirstName());
        scheduleEmployee.setUid(scheduleEmployeeDto.getUid());
        scheduleEmployee.setMobile(scheduleEmployeeDto.getMobile());
        scheduleEmployee.setPayrollSavings(scheduleEmployeeDto.getPayrollSavings());
        scheduleEmployee.setPayrollShares(scheduleEmployeeDto.getPayrollShares());

        return scheduleEmployee;
    }

    public List<ScheduleEmployeeDto> entityToDto(List<ScheduleEmployee> scheduleEmployees){
        return scheduleEmployees.stream().map(this::entityToDto).collect(Collectors.toList());
    }
    public List<ScheduleEmployee> dtoToEntity(List<ScheduleEmployeeDto> scheduleEmployees){
        return scheduleEmployees.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
