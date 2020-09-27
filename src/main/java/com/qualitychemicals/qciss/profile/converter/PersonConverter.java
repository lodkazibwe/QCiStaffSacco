package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonConverter {
    public PersonDto entityToDto(Person person){
        PersonDto personDto =new PersonDto();
        personDto.setId(person.getId());
        personDto.setResidence(person.getResidence());
        personDto.setNin(person.getNin());
        personDto.setLastName(person.getLastName());
        personDto.setFirstName(person.getFirstName());
        personDto.setGender(person.getGender());
        personDto.setEmail(person.getEmail());
        personDto.setDob(person.getDob());
        personDto.setContact(person.getContact());
        return personDto;
    }

    public Person dtoToEntity(PersonDto personDto){
        Person person =new Person();
        person.setResidence(personDto.getResidence());
        person.setNin(personDto.getNin());
        person.setLastName(personDto.getLastName());
        person.setGender(personDto.getGender());
        person.setFirstName(personDto.getFirstName());
        person.setEmail(personDto.getEmail());
        person.setDob(personDto.getDob());
        person.setContact(personDto.getContact());
        return person;
    }

    public List<PersonDto> entityToDto(List<Person> people){
        return people.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Person> dtoToEntity(List<PersonDto> personDtos){
        return personDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
