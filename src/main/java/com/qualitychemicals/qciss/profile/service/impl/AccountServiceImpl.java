package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.dao.AccountDao;
import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountDao accountDao;
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
    public void deleteSummary(int id) {

    }
}
