package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.dto.TransactionDto;
import com.qualitychemicals.qciss.transaction.model.LoanT;
import com.qualitychemicals.qciss.transaction.model.Transaction;
import com.qualitychemicals.qciss.transaction.model.TransactionStatus;
import com.qualitychemicals.qciss.transaction.model.TransactionType;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired UserService userService;

    @Override
    public Transaction verify(TransactionDto transactionDto) {
        Transaction transaction=new Transaction();
        //set status,user,date, check acct,
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(new Date());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        transaction.setUserName(userName);
        transaction.setStatus(TransactionStatus.PENDING);
        return transaction;

    }
}
