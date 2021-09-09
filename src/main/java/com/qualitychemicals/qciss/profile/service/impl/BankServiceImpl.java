package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.profile.dao.BankDao;
import com.qualitychemicals.qciss.profile.dao.UserDao;
import com.qualitychemicals.qciss.profile.dto.BankDto;
import com.qualitychemicals.qciss.profile.model.Bank;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.model.Status;
import com.qualitychemicals.qciss.profile.service.BankService;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankServiceImpl implements BankService {
    @Autowired UserService userService;
    @Autowired UserDao userDao;
    @Autowired
    BankDao bankDao;
    private final Logger logger= LoggerFactory.getLogger(BankServiceImpl.class);

    @Override
    @Transactional
    public Bank addBank(BankDto bankDto) {
        logger.info("getting profile...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Profile profile =userService.getProfile(userName);
        if(profile.getStatus()== Status.PENDING){

        if(profile.getBank()==null){
        Bank newBank=new Bank();
            newBank.setName(bankDto.getName());
            newBank.setBranch(bankDto.getBranch());
            newBank.setAccountNumber(bankDto.getAccountNumber());
            newBank.setAccountName(bankDto.getAccountName());
            profile.setBank(newBank);
        }else{
            Bank bank= profile.getBank();
            bank.setName(bankDto.getName());
            bank.setBranch(bankDto.getBranch());
            bank.setAccountNumber(bankDto.getAccountNumber());
            bank.setAccountName(bankDto.getAccountName());
            profile.setBank(bank);
        }

        logger.info("updating profile...");
        return userDao.save(profile).getBank();
        }
        return profile.getBank();

    }

    @Override
    public Bank myBank() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Profile profile =userService.getProfile(userName);
        return profile.getBank();
    }

    @Transactional
    public Bank updateBank(BankDto bankDto) {
        /*logger.info("getting profile...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Profile profile =userService.getProfile(userName);
        Bank bank= profile.getBank();
        bank.setName(bankDto.getName());
        bank.setBranch(bankDto.getBranch());
        bank.setAccountNumber(bankDto.getAccountNumber());
        bank.setAccountName(bankDto.getAccountName());
        profile.setBank(bank);
        logger.info("updating profile...");
        userDao.save(profile);*/
        return null;

    }
}
