package com.qualitychemicals.qciss.transaction.converter;

import com.qualitychemicals.qciss.transaction.dto.SavingTDto;
import com.qualitychemicals.qciss.transaction.model.SavingT;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SavingTConverter {
    public SavingTDto entityToDto(SavingT savingT){
        SavingTDto savingTDto =new SavingTDto();
        savingTDto.setDate(savingT.getDate());
        savingTDto.setSavingType(savingT.getSavingType());
        savingTDto.setAmount(savingT.getAmount());
        savingTDto.setId(savingT.getId());
        savingTDto.setUserName(savingT.getUserName());
        savingTDto.setStatus(savingT.getStatus());
        return savingTDto;

    }
    public SavingT dtoToEntity(SavingTDto savingTDto){
        SavingT savingT =new SavingT();
        savingT.setDate(savingTDto.getDate());
        savingT.setSavingType(savingTDto.getSavingType());
        savingT.setAmount(savingTDto.getAmount());
        savingT.setUserName(savingTDto.getUserName());
        savingT.setStatus(savingTDto.getStatus());
        return savingT;

    }
    public List<SavingTDto> entityToDto(List<SavingT> savingTS){
        return savingTS.stream().map(this::entityToDto).collect(Collectors.toList());

    }
    public List<SavingT> dtoToEntity(List<SavingTDto> savingTDtos){
        return savingTDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
