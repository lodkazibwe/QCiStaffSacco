package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.profile.dto.*;
import com.qualitychemicals.qciss.profile.model.Status;
import com.qualitychemicals.qciss.profile.model.TOE;
import com.qualitychemicals.qciss.profile.service.CompanyService;
import com.qualitychemicals.qciss.profile.service.PersonService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.saccoData.appConfig.AppConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StartUp implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    UserService userService;
    @Autowired PersonService personService;
    @Autowired CompanyService companyService;
    @Autowired AppConfigReader appConfigReader;
    private final Logger logger = LoggerFactory.getLogger(StartUp.class);
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("POST CONSTRUCT");
        logger.info("checking user...");
        String email=appConfigReader.getEmail();
        logger.info("start up email: " +email);
        String contact=appConfigReader.getContact();
        logger.info("start up contact: " +contact);

        int  val=personService.userExists(email,contact );
        if((val==1)||(val==2)){
            logger.info("system already initialized...");
            return;
        }
        logger.info("adding root...");
        UserDto userDto=new UserDto();
        PersonDto personDto=new PersonDto();
        personDto.setFirstName("Baluku");
        personDto.setLastName("Roben");
        personDto.setNin("NIN");
        personDto.setMobile(contact);
        personDto.setEmail(email);
        personDto.setDob(new Date());
        personDto.setGender("MALE");
        personDto.setResidence("Kampala");
        userDto.setPersonDto(personDto);

        AccountDto accountDto =new AccountDto();
        accountDto.setTotalSavings(0);
        accountDto.setTotalShares(0);
        accountDto.setPendingFee(20000);
        accountDto.setMemberNo("QC-001");
        accountDto.setPosition("Chairperson");
        userDto.setAccountDto(accountDto);

        WorkDto workDto =new WorkDto();
        workDto.setPayrollSaving(0);
        workDto.setPayrollShares(0);
        workDto.setBasicSalary(0);
        workDto.setWorkStation("Kampala");
        workDto.setEmployeeId("E1");
        workDto.setJob("ChairPerson");
        workDto.setToe(TOE.CONTRACT);
        workDto.setCompanyName("Other");
        userDto.setWorkDto(workDto);

        userService.addProfile(userDto,"ROOT", Status.OPEN);
        userService.addRole("0700123123","ADMIN");
        companyService.addCompany(new CompanyDto(1,"CiplaQCIL",50000));



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
