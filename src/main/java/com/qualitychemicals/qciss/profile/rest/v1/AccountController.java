package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.profile.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
//@CrossOrigin
//@RestController
//@RequestMapping("/profile/account")
@Component
public class AccountController {
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    AccountService accountService;
    @Autowired EmailService emailService;

    private final Logger logger= LoggerFactory.getLogger(AccountController.class);

    @GetMapping("/get")
    public ResponseEntity<AccountDto> myAccount(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        return new ResponseEntity<>(accountConverter.entityToDto
                (accountService.myAccount(userName)), HttpStatus.OK);
    }


    @PostMapping("/mail")
    public ResponseEntity<String> sendMail(){
        emailService.sendSimpleMessage("qcstaffsacco.com/webmail","webmail","CODE-1231");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }



}
