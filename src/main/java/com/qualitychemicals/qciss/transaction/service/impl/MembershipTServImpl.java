package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.dao.TransactionDao;
import com.qualitychemicals.qciss.transaction.dto.MembershipTDto;
import com.qualitychemicals.qciss.transaction.model.MembershipT;
import com.qualitychemicals.qciss.transaction.model.Transaction;
import com.qualitychemicals.qciss.transaction.service.MembershipTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MembershipTServImpl implements MembershipTService {
    @Autowired
    TransactionService transactionService;
    @Autowired UserService userService;
    @Autowired TransactionDao transactionDao;
    @Autowired AccountService accountService;
    private final Logger logger= LoggerFactory.getLogger(MembershipTServImpl.class);
    @Override
    public MembershipT payMembership(MembershipTDto membershipTDto) {

       return null;  }

    @Override
    @Transactional
    public MembershipT payMembership(double amount) {
        Transaction transaction=transactionService.receiveMobileMoney(amount);
        logger.info("preparing Transaction");
        MembershipT membershipT=new MembershipT();
        membershipT.setYear(2021);
        membershipT.setTransactionType(transaction.getTransactionType());
        membershipT.setAcctTo(transaction.getAcctTo());
        membershipT.setAcctFrom(transaction.getAcctFrom());
        membershipT.setUserName(transaction.getUserName());
        membershipT.setStatus(transaction.getStatus());
        membershipT.setDate(transaction.getDate());
        membershipT.setAmount(transaction.getAmount());
        membershipT.setId(transaction.getId());
        logger.info("updating membership fee...");
        accountService.updateMembership(amount, membershipT.getUserName());
        logger.info("savingTransaction");
        return transactionDao.save(membershipT);

    }
}
