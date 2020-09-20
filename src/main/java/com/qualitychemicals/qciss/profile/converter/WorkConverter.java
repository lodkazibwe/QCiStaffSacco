package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.DTO.WorkDTO;
import com.qualitychemicals.qciss.profile.model.Work;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkConverter {

    public WorkDTO entityToDto(Work work){
        WorkDTO workDTO=new WorkDTO();
        workDTO.setSalaryScale(work.getScale());
        workDTO.setMonthlySaving(work.getMonthlySaving());
        workDTO.setJobTittle(work.getJob());
        workDTO.setId(work.getId());
        workDTO.setCompany(work.getCompany());
        return workDTO;
    }
    public Work dtoToEntity(WorkDTO workDTO){
        if(workDTO==null){
            return new Work();
        }
        Work work =new Work();
        work.setScale(workDTO.getSalaryScale());
        work.setMonthlySaving(workDTO.getMonthlySaving());
        work.setId(workDTO.getId());
        work.setJob(workDTO.getJobTittle());
        work.setCompany(workDTO.getCompany());
        return work;
    }
    public List<WorkDTO> entityToDto(List<Work> works){
        return works.stream().map(this::entityToDto).collect(Collectors.toList());
    }
/*
    public static boolean isNull(WorkDTO workDTO) {
        return workDTO == null;
    }*/
}
