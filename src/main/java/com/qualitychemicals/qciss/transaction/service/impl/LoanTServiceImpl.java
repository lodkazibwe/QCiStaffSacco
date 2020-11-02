package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.model.LoanStatus;
import com.qualitychemicals.qciss.loan.service.LoanService;
import com.qualitychemicals.qciss.profile.model.User;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.converter.LoanTConverter;
import com.qualitychemicals.qciss.transaction.dao.TransactionDao;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.model.LoanT;
import com.qualitychemicals.qciss.transaction.model.Transaction;
import com.qualitychemicals.qciss.transaction.model.TransactionStatus;
import com.qualitychemicals.qciss.transaction.model.TransactionType;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoanTServiceImpl implements LoanTService {
    @Autowired LoanService loanService;
    @Autowired UserService userService;
    @Autowired
    TransactionService transactionService;
    @Autowired TransactionDao transactionDao;
    @Autowired LoanTConverter loanTConverter;


    @Override
    public LoanT release(int loanId, TransactionType transactionType) {
        LoanT loanT=new LoanT();
        Loan loan=loanService.getLoan(loanId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        if(loan.getStatus()== LoanStatus.APPROVED){
        loanT.setLoanId(loanId);
        loanT.setAmount(loan.getTotalDue()*-1);
        loanT.setDate(new Date());
        loanT.setStatus(TransactionStatus.PENDING);
        loanT.setTransactionType(transactionType);
        loanT.setUserName(userName);
        loanT.setFrom("qciAcct");
        loanT.setTo(loan.getBorrower());
            transact(loanT);
            loanT.setStatus(TransactionStatus.SUCCESS);
            return transactionDao.save(loanT);

        }
        else{
            throw new InvalidValuesException("Only approved LOAN can be released");
        }


    }

    private Transaction transact(Transaction transaction) {
        TransactionType transactionType=transaction.getTransactionType();
        if(transactionType==TransactionType.BANK){
            return transactBank(transaction);
        }else if(transactionType==TransactionType.MOBILE){
            return transactMobile(transaction);
        } else {
            return transactCash(transaction);

        }

    }

    private Transaction transactBank(Transaction transaction) {
        //payment api/ bank
        return transaction;
    }

    private Transaction transactMobile(Transaction transaction) {
        //payment api
        return transaction;

    }

    private Transaction transactCash(Transaction transaction) {
        return transaction;
    }

    @Override
    public LoanT repay(LoanTDto loanTDto, TransactionType transactionType) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Loan loan=loanService.getLoan(loanTDto.getLoanId());

        LoanT loanT=loanTConverter.dtoToEntity(loanTDto);
        loanT.setDate(new Date());
        loanT.setUserName(userName);
        loanT.setTransactionType(transactionType);
        loanT.setFrom(loan.getBorrower());
        loanT.setTo("QciAcct");

        User user=userService.getProfile(loanTDto.getFrom());
        if(loan.getBorrower().equals(user.getUserName())){
            if(loan.getTotalDue()<loanT.getAmount()){
                throw new InvalidValuesException("invalid Amount...");
            }

            transact(loanT);
            loanT.setStatus(TransactionStatus.SUCCESS);
        //update repayments and loan
        loanService.repay(loanTDto.getLoanId(), loanTDto.getAmount());
            return transactionDao.save(loanT);
        }else{
            throw new InvalidValuesException("invalid User or Loan...");
        }
    }

    @Override
    public LoanT payPenalty(LoanTDto loanTDto) {
        return null;
    }
}
