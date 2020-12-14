package com.qualitychemicals.qciss.saccoData.converter;

import com.qualitychemicals.qciss.saccoData.dto.DeductionScheduleDTO;
import com.qualitychemicals.qciss.saccoData.model.DeductionSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeductionScheduleConverter {
    @Autowired ScheduleLoanConverter scheduleLoanConverter;
    @Autowired ScheduleEmployeeConverter scheduleEmployeeConverter;

    public DeductionScheduleDTO entityToDto(DeductionSchedule deductionSchedule){
        DeductionScheduleDTO deductionScheduleDTO= new DeductionScheduleDTO();
        deductionScheduleDTO.setDueLoans(scheduleLoanConverter.entityToDto(deductionSchedule.getDueLoans()));
        deductionScheduleDTO.setEmployee(scheduleEmployeeConverter.entityToDto(deductionSchedule.getEmployee()));
        deductionScheduleDTO.setTotal(deductionSchedule.getTotal());
        deductionScheduleDTO.setId(deductionSchedule.getId());
        deductionScheduleDTO.setStatus(deductionSchedule.getStatus());
        return deductionScheduleDTO;
    }

    public DeductionSchedule dtoToEntity(DeductionScheduleDTO deductionScheduleDTO){
        DeductionSchedule deductionSchedule= new DeductionSchedule();
        deductionSchedule.setDueLoans(scheduleLoanConverter.dtoToEntity(deductionScheduleDTO.getDueLoans()));
        deductionSchedule.setEmployee(scheduleEmployeeConverter.dtoToEntity(deductionScheduleDTO.getEmployee()));
        deductionSchedule.setTotal(deductionScheduleDTO.getTotal());
        return deductionSchedule;
    }

    public List<DeductionScheduleDTO> entityToDto(List<DeductionSchedule> deductionSchedules){
        return deductionSchedules.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<DeductionSchedule> dtoToEntity(List<DeductionScheduleDTO> deductionScheduleDTOS){
        return deductionScheduleDTOS.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
