package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.DTO.UserDTO;
import com.qualitychemicals.qciss.profile.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserDTO entityToDto(User user){
        UserDTO userDTO =new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setPassword(user.getPassword());
        userDTO.setUserName(user.getUserName());
        return userDTO;
    }

    public User dtoToEntity (UserDTO userDTO){
        User user =new User();
        user.setUserName(userDTO.getUserName());
        user.setPassword(userDTO.getPassword());
        user.setId(userDTO.getId());
        return user;
    }

    public List<UserDTO> entityToDto(List<User> users){
        return users.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<User> dtoToEntity(List<UserDTO> userDTOs){
        return userDTOs.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
