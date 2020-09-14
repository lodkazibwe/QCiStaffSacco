package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.DTO.PersonalDTO;
import com.qualitychemicals.qciss.profile.model.Personal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonalConverter {
    public PersonalDTO entityToDto(Personal personal){
        PersonalDTO personalDTO=new PersonalDTO();
        personalDTO.setId(personal.getId());
        personalDTO.setResidence(personal.getResidence());
        personalDTO.setNIN(personal.getNIN());
        personalDTO.setLastName(personal.getLastName());
        personalDTO.setFirstName(personal.getFirstName());
        personalDTO.setGender(personal.getGender());
        personalDTO.setEmail(personal.getEmail());
        personalDTO.setDOB(personal.getDOB());
        personalDTO.setContact(personal.getContact());
        return personalDTO;
    }

    public Personal dtoToEntity(PersonalDTO personalDTO){
        Personal personal=new Personal();
        personal.setResidence(personalDTO.getResidence());
        personal.setNIN(personalDTO.getNIN());
        personal.setLastName(personalDTO.getLastName());
        personal.setFirstName(personalDTO.getFirstName());
        personal.setGender(personalDTO.getGender());
        personal.setId(personalDTO.getId());
        personal.setEmail(personalDTO.getEmail());
        personal.setDOB(personalDTO.getDOB());
        personal.setContact(personalDTO.getContact());
        return personal;
    }

    public List<PersonalDTO> entityToDto(List<Personal> personals){
        return personals.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
