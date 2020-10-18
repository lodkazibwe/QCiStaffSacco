package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.dto.WorkDto;
import com.qualitychemicals.qciss.profile.converter.UserConverter;
import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.converter.WorkConverter;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.User;
import com.qualitychemicals.qciss.profile.model.Work;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.security.AuthRequest;
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

    private final Logger logger= LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserDto> createProfile(@Valid @RequestBody PersonDto personDto){
        logger.info("starting...");
        UserDto userDTO=new UserDto();
        userDTO.setPersonDto(personDto);
        userDTO.setUserName(personDto.getMobile());
        Random random = new Random();
        String pin = String.format("%04d", random.nextInt(10000));
        userDTO.setPassword(pin);
        User user= userService.addProfile(userDTO);
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
