package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.model.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    @Autowired
    PersonConverter personConverter;
    @Autowired WorkConverter workConverter;
    @Autowired
    AccountConverter accountConverter;
    @Autowired BankConverter bankConverter;
    @Autowired BCryptPasswordEncoder passwordEncoder;
    @Autowired NextOfKinConverter nextOfKinConverter;
    private final Logger logger = LoggerFactory.getLogger(UserConverter.class);
    public UserDto entityToDto(Profile profile){
        UserDto userDto =new UserDto();
        userDto.setUserId(profile.getId());
        userDto.setUserName(profile.getUserName());
        userDto.setStatus(profile.getStatus());
        userDto.setPersonDto(personConverter.entityToDto(profile.getPerson()));
        userDto.setAccountDto(accountConverter.entityToDto(profile.getAccount()));
        userDto.setWorkDto(workConverter.entityToDto(profile.getWork()));
        userDto.setBankDto(bankConverter.entityToDto(profile.getBank()));
        userDto.setNextOfKinDto(nextOfKinConverter.entityToDto(profile.getNextOfKin()));
        return userDto;
    }

    public Profile dtoToEntity(UserDto userDto){
        Profile profile =new Profile();
        profile.setAccount(accountConverter.dtoToEntity(userDto.getAccountDto()));
        profile.setPerson(personConverter.dtoToEntity(userDto.getPersonDto()));
        profile.setWork(workConverter.dtoToEntity(userDto.getWorkDto()));
        return profile;

    }
    /*public UserDto entityToDto(Profile profile){
        UserDto userDto =new UserDto();
        userDto.setId(profile.getId());
        userDto.setUserName(profile.getUserName());
        userDto.setPassword(profile.getPassword());
        userDto.setPersonDto(personConverter.entityToDto(profile.getPerson()));
        return userDto;
    }

    public Profile dtoToEntity(UserDto userDto){
        Profile profile =new Profile();
        profile.setId(userDto.getId());
        logger.info("encoding...");
        profile.setPassword(passwordEncoder.encode(userDto.getPassword()));
        profile.setUserName(userDto.getUserName());
        profile.setPerson(personConverter.dtoToEntity(userDto.getPersonDto()));
        return profile;

    }*/

    public List<UserDto> entityToDto(List<Profile> profiles){
        return profiles.stream().map(this::entityToDto).collect(Collectors.toList());

    }

    public List<Profile> dtoToEntity(List<UserDto> userDtos){
        return userDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }


}
