package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.model.LoanStatus;
import com.qualitychemicals.qciss.loan.service.LoanService;
import com.qualitychemicals.qciss.profile.model.User;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.converter.LoanTConverter;
import com.qualitychemicals.qciss.transaction.dao.TransactionDao;
import com.qualitychemicals.qciss.transaction.dto.BankPayment;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.dto.MobilePayment;
import com.qualitychemicals.qciss.transaction.model.LoanT;
import com.qualitychemicals.qciss.transaction.model.TransactionStatus;
import com.qualitychemicals.qciss.transaction.model.TransactionType;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        loanT.setAmount(loan.getTotalDue());
        loanT.setDate(new Date());
        loanT.setStatus(TransactionStatus.PENDING);
        loanT.setTransactionType(transactionType);
        loanT.setUserName(userName);
        User borrower=userService.getProfile(loan.getBorrower());
            BankPayment bankPayment =new BankPayment();
            bankPayment.setAmount(loanT.getAmount());
            bankPayment.setReceiver(borrower.getPerson().getBank());
            bankPayment.setSender("saccoBank");
            MobilePayment mobilePayment=new MobilePayment();
            mobilePayment.setAmount(loanT.getAmount());
            mobilePayment.setReceiver(borrower.getPerson().getMobile());
            mobilePayment.setSender("saccoMobile");


        loanService.updateStatus(loanId, LoanStatus.OPEN);

        return transact(loanT, mobilePayment,bankPayment);

        }
        else{
            throw new InvalidValuesException("Only approved LOAN can be released");
        }


    }

    private LoanT transact(LoanT loanT, MobilePayment mobilePayment, BankPayment bankPayment) {
        TransactionType transactionType=loanT.getTransactionType();
        if(transactionType==TransactionType.BANK){

            return transactBank(loanT, bankPayment);

        }else if(transactionType==TransactionType.MOBILE){
            return transactMobile(loanT, mobilePayment);
        } else {
            return transactCash(loanT);
        }

    }

    private LoanT transactBank(LoanT loanT, BankPayment bankPayment) {
        //payment api/ bank
        loanT.setStatus(TransactionStatus.SUCCESS);
        return transactionDao.save(loanT);
    }

    private LoanT transactMobile(LoanT loanT, MobilePayment mobilePayment) {
        //payment api
        loanT.setStatus(TransactionStatus.SUCCESS);
        return transactionDao.save(loanT);

    }

    private LoanT transactCash(LoanT loanT) {
        loanT.setStatus(TransactionStatus.SUCCESS);
        return transactionDao.save(loanT);
    }

    @Override
    public LoanT repay(LoanTDto loanTDto, String userName, TransactionType transactionType) {
        LoanT loanT=loanTConverter.dtoToEntity(loanTDto);
        loanT.setDate(new Date());
        loanT.setTransactionType(transactionType);

        Loan loan=loanService.getLoan(loanTDto.getLoanId());
        User payee=userService.getProfile(userName);
        if(loan.getBorrower().equals(payee.getUserName())){
        BankPayment bankPayment =new BankPayment();
        bankPayment.setAmount(loanT.getAmount());
        bankPayment.setSender(payee.getPerson().getBank());
        bankPayment.setReceiver("saccoBank");
        MobilePayment mobilePayment=new MobilePayment();
        mobilePayment.setAmount(loanT.getAmount());
        mobilePayment.setSender(payee.getPerson().getBank());
        mobilePayment.setReceiver("saccoMobile");
        //transact

        transact(loanT, mobilePayment, bankPayment);
        //update repayments and loan
        loanService.repay(loanTDto.getLoanId(), loanTDto.getAmount());
        return loanT;
        }else{
            throw new InvalidValuesException("invalid User or Loan...");
        }
    }

    @Override
    public LoanT payPenalty(LoanTDto loanTDto) {
        return null;
    }
}
