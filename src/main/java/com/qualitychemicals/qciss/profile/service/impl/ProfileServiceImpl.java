package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.DAO.ProfileDAO;
import com.qualitychemicals.qciss.profile.DTO.ProfileDTO;
import com.qualitychemicals.qciss.profile.converter.ProfileConverter;
import com.qualitychemicals.qciss.profile.model.*;
import com.qualitychemicals.qciss.profile.service.ProfileService;
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
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    ProfileDAO profileDAO;
    @Autowired UserService userService;
    @Autowired ProfileConverter profileConverter;
    private final Logger logger =LoggerFactory.getLogger(ProfileService.class);


    @Override
    public Profile addProfile(ProfileDTO profileDTO) {
        logger.info("checking profile...");
        String userName=profileDTO.getUserDetail().getUserName();
        boolean bull=userService.usernameExists(userName);
        if(bull){
            logger.error("user name already taken...");
            throw new ResourceNotFoundException("User Name Already Taken... NAME: "+userName);
        }else {
            logger.info("profile validated...");
            logger.info("updating...");
            Role role = new Role("USER");
            Set<Role> roles = new HashSet<>(Collections.singletonList(role));

            Profile profile = profileConverter.dtoToEntity(profileDTO);
            profile.getUser().setRole(roles);
            profile.getUser().setStatus("PENDING");
            profile.getPersonal().setImage("C:\\Users\\joeko\\Desktop\\file\\default.png");
            logger.info("profile values updated...");
            logger.info("saving...");
            Profile profileSaved = profileDAO.save(profile);
            logger.info("profile created...");
            return profileSaved;
        }


    }

    @Override
    public Profile updateProfile(Profile profile) {
        return profileDAO.save(profile);
    }

    @Override
    public Work getWorkInfo(int id) {
        Profile profile=profileDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No Profile With ID: " +id));
        return profile.getWork();
    }

    @Override
    public Summary getSummary(int id) {
        Profile profile=profileDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No Profile With ID: " +id));
        return profile.getSummary();
    }

    @Override
    public List<Account> getAccounts(int id) {
        Profile profile=profileDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No Profile With ID: " +id));
        return profile.getAccount();
    }

    @Override
    public Profile getProfile(int id) {
        return profileDAO.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No Profile With ID: "+id));
    }

    @Override
    public List<Profile> getAll() {
        return profileDAO.findAll();
    }

    @Override
    public void deleteProfile(int id) {

    }
}
