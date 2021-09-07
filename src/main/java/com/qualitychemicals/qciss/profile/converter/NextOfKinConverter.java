package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.dto.NextOfKinDto;
import com.qualitychemicals.qciss.profile.model.NextOfKin;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NextOfKinConverter {
    public NextOfKinDto entityToDto(NextOfKin nextOfKin){
        if(nextOfKin==null){
            return new NextOfKinDto();
        }
        NextOfKinDto nextOfKinDto =new NextOfKinDto();
        nextOfKinDto.setContact(nextOfKin.getContact());
        nextOfKinDto.setName(nextOfKin.getName());
        nextOfKinDto.setRelationship(nextOfKin.getRelationship());
        return nextOfKinDto;

    }

    public NextOfKin dtoToEntity(NextOfKinDto nextOfKinDto){
        NextOfKin nextOfKin =new NextOfKin();
        nextOfKin.setContact(nextOfKinDto.getContact());
        nextOfKin.setName(nextOfKinDto.getName());
        nextOfKin.setRelationship(nextOfKinDto.getRelationship());
        return nextOfKin;
    }

    public List<NextOfKinDto> entityToDto(List<NextOfKin> nextOfKins){
        return nextOfKins.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<NextOfKin> dtoToEntity(List<NextOfKinDto> nextOfKinDtos){
        return nextOfKinDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
