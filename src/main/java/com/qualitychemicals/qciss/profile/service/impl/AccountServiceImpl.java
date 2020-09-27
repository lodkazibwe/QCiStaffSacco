package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.dao.AccountDao;
import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.User;
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
    @Autowired
    UserService userService;
    @Autowired AccountConverter accountConverter;

    private final Logger logger= LoggerFactory.getLogger(AccountService.class);

    @Override
    @Transactional
    public List<Account> addAccount(AccountDto accountDTO, int id) {
        Account account=accountConverter.dtoToEntity(accountDTO);

        logger.info("getting user...");
        User user = userService.getProfile(id);
        List<Account> accounts= user.getAccount();
        accounts.add(account);
        user.setAccount(accounts);
        logger.info("saving Account...");
        User updatedUser = userService.updateProfile(user);
        logger.info("saved...");
        return updatedUser.getAccount();
    }

    @Override
    public Account getAccount(int id) {
        return accountDao.findById(id).orElseThrow(()->new ResourceNotFoundException("No such account with ID: "+id));
    }


    @Override
    public List<Account> getByCategory(String category) {
        return accountDao.findByCategory(category);
    }

    @Override
    public List<Account> getAll() {
        return accountDao.findAll();
    }

    @Override
    public Account updateAccount(AccountDto accountDTO, int id) {

        Account account=accountDao.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No such account with ID: "+id));
        account.setAccountName(accountDTO.getAccountName());
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setCategory(accountDTO.getCategory());
        return accountDao.save(account);
    }

    @Override
    public void deleteAccount(int id) {

    }
}
