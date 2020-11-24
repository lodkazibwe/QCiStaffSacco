package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.dao.TransactionDao;
import com.qualitychemicals.qciss.transaction.model.*;
import com.qualitychemicals.qciss.transaction.service.SavingTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SavingTServiceImpl implements SavingTService {
    @Autowired UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @Autowired TransactionDao transactionDao;
    private final Logger logger = LoggerFactory.getLogger(SavingTServiceImpl.class);
    @Override
    @Transactional
    public SavingT mobileSaving(double amount) {
        logger.info("transacting...");
        Transaction transaction=transactionService.receiveMobileMoney(amount);
        logger.info("setting transaction...");
        SavingT savingT=new SavingT();
        savingT.setSavingType(SavingType.DIRECT);
        savingT.setTransactionType(transaction.getTransactionType());
        savingT.setAcctTo(transaction.getAcctTo());
        savingT.setAcctFrom(transaction.getAcctFrom());
        savingT.setUserName(transaction.getUserName());
        savingT.setStatus(transaction.getStatus());
        savingT.setDate(transaction.getDate());
        savingT.setAmount(transaction.getAmount());
        savingT.setId(transaction.getId());
        logger.info("updating saving...");
        accountService.updateSaving(amount, transaction.getUserName());
        logger.info("saving transaction...");
        return transactionDao.save(savingT);
    }
}
