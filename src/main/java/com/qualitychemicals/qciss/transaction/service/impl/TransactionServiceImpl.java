package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.dao.TransactionDao;
import com.qualitychemicals.qciss.transaction.dto.MobilePayment;
import com.qualitychemicals.qciss.transaction.model.*;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired UserService userService;
    @Autowired TransactionDao transactionDao;
    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    @Transactional
    public Transaction receiveMobileMoney(double amount) {
        logger.info("getting user");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Profile profile =userService.getProfile(userName);
        logger.info("setting payment...");
        MobilePayment mobilePayment=new MobilePayment();
        String mobile= profile.getPerson().getMobile();
        mobilePayment.setTo("qciAcct");
        mobilePayment.setFrom(mobile);
        mobilePayment.setAmount(amount);

        transactMobile(mobilePayment);
        logger.info("setting transaction...");
        Transaction transaction=new Transaction();
        transaction.setAmount(amount);
        transaction.setDate(new Date());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setTransactionType(TransactionType.MOBILE);
        transaction.setUserName(userName);
        transaction.setAcctFrom(mobile);
        transaction.setAcctTo("qciAcct");
       return transaction;
    }

    public  MobilePayment transactMobile(MobilePayment mobilePayment) {
        //payment service
        logger.info("connecting to payment service");
        return mobilePayment;

    }

    @Override
    @Transactional
    public List<LoanT> loanTransactions(int loanId) {
        return transactionDao.findByLoanId(loanId);
    }

    @Override
    @Transactional
    public List<Transaction> userTransactions(int userId) {
        Profile profile=userService.getProfile(userId);
        String mobile=profile.getPerson().getMobile();
        return transactionDao.findByAcctFrom(mobile);
    }
    }
