package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.dao.UserDao;
import com.qualitychemicals.qciss.profile.dto.*;
import com.qualitychemicals.qciss.profile.converter.UserConverter;
import com.qualitychemicals.qciss.profile.model.*;
import com.qualitychemicals.qciss.profile.service.CompanyService;
import com.qualitychemicals.qciss.profile.service.EmailService;
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
    UserConverter userConverter;
    @Autowired BCryptPasswordEncoder passwordEncoder;
    private final Logger logger =LoggerFactory.getLogger(UserService.class);
    @Autowired
    CompanyService companyService;
    @Autowired EmailService emailService;



    @Transactional
    @Override
    public User addProfile(UserDto userDTO, String rol, Status status) {
        logger.info("checking profile...");
        String userName= userDTO.getPersonDto().getMobile();
        boolean bull=userDAO.existsByUserName(userName);
        if(bull){
            logger.error("user already exists...");
            throw new ResourceNotFoundException("user already exists... No.: "+userName);
        }else {
            logger.info("setting company...");
           String company= verifyCompany(userDTO.getWorkDto().getCompanyName());
           userDTO.getWorkDto().setCompanyName(company);
            logger.info("converting...");
            User user = userConverter.dtoToEntity(userDTO);
            logger.info("user validated...");
            String email=userDTO.getPersonDto().getEmail();

            logger.info("updating...");
            Set<Role> roles=getRoles(rol);
            user.setRole(roles);
            user.setUserName(userName);
            user.setStatus(status);
            Random random = new Random();
            String pin = String.format("%04d", random.nextInt(10000));
            user.setPassword(passwordEncoder.encode(pin));

            String message="one time login pin QCiSS "+"Q-"+pin;
            user.getPerson().setImage("C:\\Users\\joeko\\Desktop\\file\\default.png");
            logger.info("user values updated...");
            logger.info("saving...");
            User savedUser = userDAO.save(user);
            emailService.sendSimpleMessage(email,"PRIVATE-QCi-CODE",message);
            logger.info(message+" for  "+ userName);
            logger.info("user created...");
            return savedUser;

        }

    }

    @Override
    public User signUp(PersonDto personDto) {
        UserDto userDto=new UserDto();
        userDto.setPersonDto(personDto);
        WorkDto workDto=new WorkDto();
        AccountDto accountDto=new AccountDto();
        accountDto.setPendingFee(20000);
        accountDto.setTotalSavings(0);
        accountDto.setTotalShares(0);
        userDto.setAccountDto(accountDto);
        userDto.setWorkDto(workDto);
        return addProfile(userDto,"USER", Status.PENDING);
    }

    private String verifyCompany(String companyName) {
       boolean bull=companyService.checkCompany(companyName);
        if(bull){
                return companyName;
        }else {
            logger.info("default company...");
            return "DEFAULT";
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
    public Work getWorkInfo(String userName) {
        User user = userDAO.findByUserName(userName);
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
        logger.info(message+" for  "+ contact);
        User user=getProfile(contact);
        String email=user.getPerson().getEmail();
        emailService.sendSimpleMessage(email,"PRIVATE-QCi-CODE",message);
        return updatePass(contact, pin);

    }

    @Override
    public String verifyAccount(String accountNumber, String userName) {
        User user=getProfile(userName);
        String mobile=user.getPerson().getMobile();

        if(accountNumber.equals(mobile)){
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
    public boolean isUserOpen(String userName) {
        User user=getProfile(userName);
        if(user.getStatus()==Status.OPEN){return true;
        } else{return false;}
    }

    @Override
    public boolean isUserClosed(String userName) {
        User user=getProfile(userName);
        if(user.getStatus()==Status.CLOSED){return true;
        } else{return false;}
    }

    @Override
    public void deleteProfile(int id) {

    }
    private Set<Role> getRoles(String rol){
    Role role = new Role(rol);
        return new HashSet<>(Collections.singletonList(role));
    }
}
