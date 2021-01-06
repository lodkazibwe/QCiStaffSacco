package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.dto.*;
import com.qualitychemicals.qciss.profile.converter.UserConverter;
import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.converter.WorkConverter;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.Status;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.model.Work;
import com.qualitychemicals.qciss.profile.service.EmailService;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@CrossOrigin
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
        Profile profile = userService.signUp(personDto);
        return new ResponseEntity<>(userConverter.entityToDto(profile), HttpStatus.OK);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<UserDto> createProfile(@Valid @RequestBody UserDto userDto){
        logger.info("starting...");
        Profile profile = userService.addProfile(userDto, "USER", Status.OPEN);
        return new ResponseEntity<>(userConverter.entityToDto(profile), HttpStatus.OK);
    }

   /* @PostMapping("/addRoot")
    public ResponseEntity<UserDto> defaultUser(@Valid @RequestBody UserDto userDto){

        String rootEmail="lordkazibwe@gmail.com";
        userDto.getPersonDto().setEmail(rootEmail);
        logger.info("starting...");
        Profile profile = userService.addProfile(userDto, "ROOT", Status.OPEN);
        return new ResponseEntity<>(userConverter.entityToDto(profile), HttpStatus.OK);

    }*/
    @PostMapping("addAdmin")
    public ResponseEntity<UserDto> addAdmin(@Valid @RequestBody UserDto userDto){
        logger.info("starting...");
        Profile profile = userService.addProfile(userDto, "ADMIN", Status.OPEN);
        return new ResponseEntity<>(userConverter.entityToDto(profile), HttpStatus.OK);

    }

    @GetMapping("/get")
    public ResponseEntity<UserDto> myProfile(){
        logger.info("getting user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info(userName);
        return new ResponseEntity<>(userConverter.entityToDto
                (userService.getProfile(userName)), HttpStatus.OK);
    }

    @GetMapping("/admin/getAdmins")
    public ResponseEntity<List<PersonDto>> getAdmins(){
        return new ResponseEntity<>(
                userService.getAdmins(), HttpStatus.OK);
    }


    @GetMapping("/admin/get/{id}")
    public ResponseEntity<UserDto> getProfile(@PathVariable int id){
        return new ResponseEntity<>(userConverter.entityToDto
                (userService.getProfile(id)), HttpStatus.OK);
    }
    @GetMapping("/admin/getByName/{userName}")
    public ResponseEntity<UserDto> getProfile(@PathVariable String userName){
        return new ResponseEntity<>(userConverter.entityToDto
                (userService.getProfile(userName)), HttpStatus.OK);
    }
    @GetMapping("/admin/getAll")
    public ResponseEntity<List<UserDto>> getAll(){
        return new ResponseEntity<>(userConverter.entityToDto(userService.getAll()),
                HttpStatus.OK);
    }
    @GetMapping("/admin/getPendingUsers")
    public ResponseEntity<List<UserDto>> getAllPending(){
        return new ResponseEntity<>(userConverter.entityToDto(userService.getAllPending()),
                HttpStatus.OK);
    }
    @GetMapping("/admin/getClosedUsers")
    public ResponseEntity<List<UserDto>> getAllClosed(){
        return new ResponseEntity<>(userConverter.entityToDto(userService.getAllClosed()),
                HttpStatus.OK);
    }
    @GetMapping("/admin/getOpenUsers")
    public ResponseEntity<List<UserDto>> getAllOpen(){
        return new ResponseEntity<>(userConverter.entityToDto(userService.getAllOpen()),
                HttpStatus.OK);
    }


    @GetMapping("/admin/getAccount/{userId}")
    public ResponseEntity<AccountDto> getUserAccount(@PathVariable int userId){
        Account account = userService.getSummary(userId);
        return new ResponseEntity<>(accountConverter.entityToDto(account), HttpStatus.OK);
    }

    @GetMapping("/admin/getWork/{userId}")
    public ResponseEntity<WorkDto> getWorkInfo(@PathVariable int userId){
        Work work= userService.getWorkInfo(userId);
        return new ResponseEntity<>(workConverter.entityToDto(work), HttpStatus.OK);

    }
    @GetMapping("/admin/getEmployees/{company}")
    public ResponseEntity<List<EmployeeDto>> getEmployees(@PathVariable String company){
        List<EmployeeDto> employees= userService.getEmployees(company);
        return new ResponseEntity<>(employees, HttpStatus.OK);

    }
    @GetMapping("/admin/deductionSchedule/{company}")
    public ResponseEntity<List<DeductionScheduleDTO>> deductionSchedule(@PathVariable String company){
        List<DeductionScheduleDTO> deductionSchedules= userService.deductionSchedule(company);
        return new ResponseEntity<>(deductionSchedules, HttpStatus.OK);

    }

    @PutMapping("/admin/verify/{userId}")
    public ResponseEntity<UserDto> verifyUser(@PathVariable int userId){
        return new ResponseEntity<>(userConverter.entityToDto(userService.verifyUser(userId)), HttpStatus.OK);
    }
    @PutMapping("/admin/close/{userId}")
    public ResponseEntity<UserDto> closeUser(@PathVariable int userId){
        return new ResponseEntity<>(userConverter.entityToDto(userService.closeUser(userId)), HttpStatus.OK);
    }

}
