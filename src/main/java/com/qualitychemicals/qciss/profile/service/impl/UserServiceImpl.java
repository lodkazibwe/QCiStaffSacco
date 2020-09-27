package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.dao.UserDao;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.converter.UserConverter;
import com.qualitychemicals.qciss.profile.model.*;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDAO;
    @Autowired
    UserConverter profileConverter;
    private final Logger logger =LoggerFactory.getLogger(UserService.class);


    @Override
    public User addProfile(UserDto userDTO) {
        logger.info("checking profile...");
        String userName= userDTO.getUserName();
        boolean bull=userDAO.existsByUserName(userName);
        if(bull){
            logger.error("user name already taken...");
            throw new ResourceNotFoundException("User Name Already Taken... NAME: "+userName);
        }else {
            logger.info("user validated...");
            logger.info("updating...");
            Role role = new Role("USER");
            Set<Role> roles = new HashSet<>(Collections.singletonList(role));

            User user = profileConverter.dtoToEntity(userDTO);
            user.setRole(roles);
            user.setStatus("PENDING");
            user.getPerson().setImage("C:\\Users\\joeko\\Desktop\\file\\default.png");
            logger.info("user values updated...");
            logger.info("saving...");
            User savedUser = userDAO.save(user);
            logger.info("user created...");
            return savedUser;
        }


    }

    @Override
    public User updateProfile(User user) {
        return userDAO.save(user);
    }

    @Override
    public Work getWorkInfo(int id) {
        User user = userDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No User With ID: " +id));
        return user.getWork();
    }

    @Override
    public Summary getSummary(int id) {
        User user = userDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No User With ID: " +id));
        return user.getSummary();
    }

    @Override
    public List<Account> getAccounts(int id) {
        User user = userDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No User With ID: " +id));
        return user.getAccount();
    }

    @Override
    public User getProfile(int id) {
        return userDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No User With ID: "+id));
    }

    @Override
    public List<User> getAll() {
        return userDAO.findAll();
    }

    @Override
    public void deleteProfile(int id) {

    }
}
