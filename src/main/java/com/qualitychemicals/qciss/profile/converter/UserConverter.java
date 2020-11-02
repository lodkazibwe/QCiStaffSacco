package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.model.User;
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
    @Autowired BCryptPasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserConverter.class);
    public UserDto entityToDto(User user){
        UserDto userDto =new UserDto();
        userDto.setPersonDto(personConverter.entityToDto(user.getPerson()));
        userDto.setAccountDto(accountConverter.entityToDto(user.getAccount()));
        userDto.setWorkDto(workConverter.entityToDto(user.getWork()));
        return userDto;
    }

    public User dtoToEntity(UserDto userDto){
        User user =new User();
        user.setAccount(accountConverter.dtoToEntity(userDto.getAccountDto()));
        user.setPerson(personConverter.dtoToEntity(userDto.getPersonDto()));
        user.setWork(workConverter.dtoToEntity(userDto.getWorkDto()));
        return user;

    }
    /*public UserDto entityToDto(User user){
        UserDto userDto =new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setPersonDto(personConverter.entityToDto(user.getPerson()));
        return userDto;
    }

    public User dtoToEntity(UserDto userDto){
        User user =new User();
        user.setId(userDto.getId());
        logger.info("encoding...");
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserName(userDto.getUserName());
        user.setPerson(personConverter.dtoToEntity(userDto.getPersonDto()));
        return user;

    }*/

    public List<UserDto> entityToDto(List<User> users){
        return users.stream().map(this::entityToDto).collect(Collectors.toList());

    }

    public List<User> dtoToEntity(List<UserDto> userDtos){
        return userDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }


}
