package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.dao.UserDao;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.converter.UserConverter;
import com.qualitychemicals.qciss.profile.model.*;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDAO;
    @Autowired
    UserConverter profileConverter;
    @Autowired BCryptPasswordEncoder passwordEncoder;
    private final Logger logger =LoggerFactory.getLogger(UserService.class);


    @Transactional
    @Override
    public User addProfile(UserDto userDTO) {
        logger.info("checking profile...");
        String userName= userDTO.getUserName();
        boolean bull=userDAO.existsByUserName(userName);
        if(bull){
            logger.error("user already exists...");
            throw new ResourceNotFoundException("user already exists... No.: "+userName);
        }else {
            String pin=userDTO.getPassword();
            String message=pin+" one time login pin QCiSS";
            String receiver=userName;

            logger.info("user validated...");
            logger.info("updating...");
            Role role = new Role("USER");
            Set<Role> roles = new HashSet<>(Collections.singletonList(role));
            logger.info("converting...");
            User user = profileConverter.dtoToEntity(userDTO);
            user.setRole(roles);
            user.setStatus("PENDING");
            user.getPerson().setImage("C:\\Users\\joeko\\Desktop\\file\\default.png");
            logger.info("user values updated...");
            logger.info("saving...");
            User savedUser = userDAO.save(user);
            //send message
            logger.info(pin+" one time login pin QCiSS");
            logger.info("user created...");
            return savedUser;

        }


    }

    @Override
    public User updateProfile(User user) {
        return userDAO.save(user);
    }

    @Override
    public String createPass(String pass) {
        logger.info("getting user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        return updatePass(userName, pass);
    }

    @Override
    public String updatePass(String userName, String pass) {
        logger.info("updating password...");
        User user=getProfile(userName);
        user.setPassword(passwordEncoder.encode(pass));
        userDAO.save(user);logger.info("updated...");
        return"success";

    }

    @Transactional
    @Override
    public Work getWorkInfo(int id) {
        User user = userDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No User With ID: " +id));
        return user.getWork();
    }

    @Override
    public Account getSummary(int id) {
        User user = userDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No User With ID: " +id));
        return user.getAccount();
    }

    @Override
    public User getProfile(String userName) {

        User user=userDAO.findByUserName(userName);
        if (user==null){
            throw new ResourceNotFoundException("No User With No.: " +userName);
        }else{
        return user;}
    }

    @Override
    public String requestPin(String contact) {
        logger.info("generating pin...");
        Random random = new Random();
        String pin = String.format("%04d", random.nextInt(10000));
        logger.info("sending message...");
        logger.info(pin+" one time login pin QCiSS");
        String message=pin+" one time login pin QCiSS";
        String receiver=contact;
        //message api
        return updatePass(contact, pin);

    }

    @Override
    public String verifyAccount(String accountNumber, String userName) {
        User user=getProfile(userName);
        String mobile=user.getPerson().getMobile();
        String bank=user.getPerson().getBank();
        if(accountNumber.equals(mobile) || accountNumber.equals(bank)){
            return accountNumber;
        }
        throw new InvalidValuesException("invalid user accountNumber: "+accountNumber);

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
