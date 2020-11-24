package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/profile/account")
public class AccountController {
    @Autowired
    AccountConverter accountConverter;
    @Autowired
    AccountService accountService;

    private final Logger logger= LoggerFactory.getLogger(AccountController.class);

    @GetMapping("/get")
    public ResponseEntity<AccountDto> myAccount(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        return new ResponseEntity<>(accountConverter.entityToDto
                (accountService.myAccount(userName)), HttpStatus.OK);
    }


}
