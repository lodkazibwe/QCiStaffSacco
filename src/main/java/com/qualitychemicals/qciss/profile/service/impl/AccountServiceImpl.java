package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.dao.AccountDao;
import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDao accountDao;
    @Autowired UserService userService;
    private final Logger logger= LoggerFactory.getLogger(AccountServiceImpl.class);
    @Override
    public Account updateAccount(AccountDto accountDto, int id) {
        logger.info("getting current details...");
        Account account =getSummary(id);
        logger.info("updating...");
        account.setPendingFee(accountDto.getPendingFee());
        account.setSavings(accountDto.getTotalSavings());
        account.setShares(accountDto.getTotalShares());
        return accountDao.save(account);
    }

    @Override
    public Account getSummary(int id) {
        return accountDao.findById(id).orElseThrow(()->new ResourceNotFoundException("Not Found: "+id));
    }


    @Override
    public List<Account> getAll() {
        return accountDao.findAll();
    }

    @Override
    @Transactional
    public void updateSaving(double amount, String userName) {
        logger.info("getting account ...");
        Profile profile =userService.getProfile(userName);
        Account account= profile.getAccount();
        logger.info("updating ...");
        double saving=account.getSavings();
        account.setSavings(saving+amount);
        logger.info("saving ...");
        accountDao.save(account);

    }

    @Override
    @Transactional
    public void updateMembership(double amount, String userName) {
        logger.info(userName);
        Profile profile =userService.getProfile(userName);
        Account account= profile.getAccount();
        double membership=account.getPendingFee();
        account.setPendingFee(membership-amount);
        accountDao.save(account);

    }

    @Override
    public void updateShares(double shares, String userName) {
        Profile profile =userService.getProfile(userName);
        Account account= profile.getAccount();
        double share=account.getShares();
        account.setShares(share+shares);
        accountDao.save(account);

    }

    @Override
    public Account myAccount(String userName) {
        logger.info("getting user ");
        Profile profile=userService.getProfile(userName);
        logger.info("getting account ");
        return profile.getAccount();
    }

    @Override
    public void deleteSummary(int id) {

    }
}
