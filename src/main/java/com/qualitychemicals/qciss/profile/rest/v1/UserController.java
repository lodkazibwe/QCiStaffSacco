package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.dto.*;
import com.qualitychemicals.qciss.profile.converter.UserConverter;
import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.converter.WorkConverter;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.Status;
import com.qualitychemicals.qciss.profile.model.User;
import com.qualitychemicals.qciss.profile.model.Work;
import com.qualitychemicals.qciss.profile.service.EmailService;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/profile/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserConverter userConverter;
    @Autowired
    AccountConverter accountConverter;
    @Autowired WorkConverter workConverter;
    @Autowired EmailService emailService;

    private final Logger logger= LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signUp")
    @Transactional
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody PersonDto personDto){
        logger.info("starting...");
        User user= userService.signUp(personDto);
        return new ResponseEntity<>(userConverter.entityToDto(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserDto> createProfile(@Valid @RequestBody UserDto userDto){
        logger.info("starting...");
        User user= userService.addProfile(userDto, "USER", Status.OPEN);
        return new ResponseEntity<>(userConverter.entityToDto(user), HttpStatus.OK);
    }

    @PostMapping("/defaultUser")
    public ResponseEntity<UserDto> defaultUser(@Valid @RequestBody UserDto userDto){
        //check if exists
        logger.info("starting...");
        User user= userService.addProfile(userDto, "ADMIN", Status.OPEN);
        return new ResponseEntity<>(userConverter.entityToDto(user), HttpStatus.OK);

    }


    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getProfile(@PathVariable int id){
        return new ResponseEntity<>(userConverter.entityToDto
                (userService.getProfile(id)), HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAll(){
        return new ResponseEntity<>(userConverter.entityToDto(userService.getAll()),
                HttpStatus.OK);
    }

    @GetMapping("/getSummary/{userId}")
    public ResponseEntity<AccountDto> getUserSummary(@PathVariable int userId){
        Account account = userService.getSummary(userId);
        return new ResponseEntity<>(accountConverter.entityToDto(account), HttpStatus.OK);
    }

    @GetMapping("/getWork/{userId}")
    public ResponseEntity<WorkDto> getWorkInfo(@PathVariable int userId){
        Work work= userService.getWorkInfo(userId);
        return new ResponseEntity<>(workConverter.entityToDto(work), HttpStatus.OK);

    }

}
