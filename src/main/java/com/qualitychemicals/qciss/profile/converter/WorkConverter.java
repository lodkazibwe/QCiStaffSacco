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
        workDTO.setSalaryScale(work.getScale());
        workDTO.setMonthlySaving(work.getMonthlySaving());
        workDTO.setJobTittle(work.getJob());
        workDTO.setId(work.getId());
        workDTO.setCompanyName(work.getCompanyName());
        return workDTO;
    }
    public Work dtoToEntity(WorkDto workDto){
        if(workDto==null){
            return new Work();
        }
        Work work =new Work();
        work.setScale(workDto.getSalaryScale());
        work.setMonthlySaving(workDto.getMonthlySaving());
        work.setId(workDto.getId());
        work.setCompanyName(workDto.getCompanyName());
        work.setJob(workDto.getJobTittle());
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
