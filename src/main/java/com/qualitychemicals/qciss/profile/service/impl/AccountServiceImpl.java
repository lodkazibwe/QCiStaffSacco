package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.DAO.AccountDAO;
import com.qualitychemicals.qciss.profile.DTO.AccountDTO;
import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.profile.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDAO accountDAO;
    @Autowired
    ProfileService profileService;
    @Autowired AccountConverter accountConverter;

    private final Logger logger= LoggerFactory.getLogger(AccountService.class);

    @Override
    @Transactional
    public List<Account> addAccount(AccountDTO accountDTO, int id) {
        Account account=accountConverter.dtoToEntity(accountDTO);

        logger.info("getting profile...");
        Profile profile =profileService.getProfile(id);
        List<Account> accounts=profile.getAccount();
        accounts.add(account);
        profile.setAccount(accounts);
        logger.info("saving Account...");
        Profile updatedProfile=profileService.updateProfile(profile);
        logger.info("saved...");
        return updatedProfile.getAccount();
    }

    @Override
    public Account getAccount(int id) {
        return accountDAO.findById(id).orElseThrow(()->new ResourceNotFoundException("No such account with ID: "+id));
    }


    @Override
    public List<Account> getByCategory(String category) {
        return accountDAO.findByCategory(category);
    }

    @Override
    public List<Account> getAll() {
        return accountDAO.findAll();
    }

    @Override
    public Account updateAccount(AccountDTO accountDTO, int id) {
        //Account account1=accountConverter.dtoToEntity(accountDTO);
        Account account=accountDAO.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No such account with ID: "+id));
        account.setAccountName(accountDTO.getAccountName());
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setCategory(accountDTO.getCategory());
        return accountDAO.save(account);
    }

    @Override
    public void deleteAccount(int id) {

    }
}
