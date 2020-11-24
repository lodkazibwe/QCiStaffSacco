package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.dto.WorkDto;
import com.qualitychemicals.qciss.profile.model.Work;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkConverter {

    public WorkDto entityToDto(Work work){
        WorkDto workDTO=new WorkDto();
        workDTO.setBasicSalary(work.getBasicSalary());
        workDTO.setPayrollSaving(work.getPayrollSaving());
        workDTO.setPayrollShares(work.getPayrollShares());
        workDTO.setJob(work.getJob());
        workDTO.setId(work.getId());
        workDTO.setCompanyName(work.getCompanyName());
        workDTO.setEmployeeId(work.getEmployeeId());
        workDTO.setWorkStation(work.getWorkStation());
        return workDTO;
    }
    public Work dtoToEntity(WorkDto workDto){
        if(workDto==null){
            return new Work();
        }
        Work work =new Work();
        work.setBasicSalary(workDto.getBasicSalary());
        work.setPayrollSaving(workDto.getPayrollSaving());
        work.setPayrollShares(workDto.getPayrollShares());
        work.setId(workDto.getId());
        work.setCompanyName(workDto.getCompanyName());
        work.setJob(workDto.getJob());
        work.setEmployeeId(workDto.getEmployeeId());
        work.setWorkStation(workDto.getWorkStation());
        return work;
    }
    public List<WorkDto> entityToDto(List<Work> works){
        return works.stream().map(this::entityToDto).collect(Collectors.toList());
    }
/*
    public static boolean isNull(WorkDto workDTO) {
        return workDTO == null;
    }*/
}
