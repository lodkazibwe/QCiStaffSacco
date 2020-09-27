package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.dto.SummaryDto;
import com.qualitychemicals.qciss.profile.dto.WorkDto;
import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.converter.UserConverter;
import com.qualitychemicals.qciss.profile.converter.SummaryConverter;
import com.qualitychemicals.qciss.profile.converter.WorkConverter;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.Summary;
import com.qualitychemicals.qciss.profile.model.Work;
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
    UserConverter profileConverter;
    @Autowired AccountConverter accountConverter;
    @Autowired SummaryConverter summaryConverter;
    @Autowired WorkConverter workConverter;

    private final Logger logger= LoggerFactory.getLogger(UserController.class);

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserDto> createProfile(@Valid @RequestBody UserDto userDTO){
        logger.info("starting...");
        return new ResponseEntity<>(profileConverter.entityToDto(
                userService.addProfile(userDTO)), HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDto> getProfile(@PathVariable int id){
        return new ResponseEntity<>(profileConverter.entityToDto
                (userService.getProfile(id)), HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAll(){
        return new ResponseEntity<>(profileConverter.entityToDto(userService.getAll()),
                HttpStatus.OK);
    }
    @GetMapping("/getAccounts/{userId}")
    public ResponseEntity<List<AccountDto>> getUserAccounts(@PathVariable int userId){
        List<Account> accounts= userService.getAccounts(userId);
        return new ResponseEntity<>(accountConverter.entityToDto(accounts), HttpStatus.OK);
    }
    @GetMapping("/getSummary/{userId}")
    public ResponseEntity<SummaryDto> getUserSummary(@PathVariable int userId){
        Summary summary= userService.getSummary(userId);
        return new ResponseEntity<>(summaryConverter.entityToDto(summary), HttpStatus.OK);
    }

    @GetMapping("/getWork/{userId}")
    public ResponseEntity<WorkDto> getWorkInfo(@PathVariable int userId){
        Work work= userService.getWorkInfo(userId);
        return new ResponseEntity<>(workConverter.entityToDto(work), HttpStatus.OK);

    }

}
