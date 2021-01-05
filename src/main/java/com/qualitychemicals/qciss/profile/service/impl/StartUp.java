package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.model.Status;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StartUp implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    UserService userService;
    private final Logger logger = LoggerFactory.getLogger(StartUp.class);
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("postConstruct");

       /* UserDto userDto=new UserDto();
        userDto.setUserName("admin");
        PersonDto personDto=new PersonDto();
        personDto.setEmail("qckazibwe@gmail.com");
        personDto.setMobile("0708252167");


        userDto.getPersonDto().setEmail("qckazibwe@gmail.com");
        userService.addProfile(userDto,"ROOT", Status.OPEN);
        //userService.addRole("admin","ADMIN");
        return;*/
    }

    /*public Profile signUp(PersonDto personDto) {
        logger.info("converting...");

        userDto.setPersonDto(personDto);
        WorkDto workDto=new WorkDto();
        AccountDto accountDto=new AccountDto();
        accountDto.setPendingFee(20000);
        accountDto.setTotalSavings(0);
        accountDto.setTotalShares(0);
        accountDto.setPosition("member");
        userDto.setAccountDto(accountDto);
        userDto.setWorkDto(workDto);
        logger.info("saving...");
        return addProfile(userDto,"USER", Status.PENDING);
    }*/


}
