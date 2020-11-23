package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.model.LoanStatus;
import com.qualitychemicals.qciss.loan.service.LoanService;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.converter.LoanTConverter;
import com.qualitychemicals.qciss.transaction.dao.TransactionDao;
import com.qualitychemicals.qciss.transaction.dto.LoanPayDto;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.dto.MobilePayment;
import com.qualitychemicals.qciss.transaction.model.LoanT;
import com.qualitychemicals.qciss.transaction.model.TransactionStatus;
import com.qualitychemicals.qciss.transaction.model.TransactionType;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class LoanTServiceImpl implements LoanTService {
    @Autowired LoanService loanService;
    @Autowired UserService userService;
    @Autowired
    TransactionService transactionService;
    @Autowired TransactionDao transactionDao;
    @Autowired LoanTConverter loanTConverter;
    private final Logger logger = LoggerFactory.getLogger(LoanTServiceImpl.class);

    @Override
    @Transactional
    public LoanT release(int loanId, TransactionType transactionType) {
        LoanT loanT=new LoanT();
        logger.info("getting loan...");
        Loan loan=loanService.getLoan(loanId);
        logger.info("getting admin user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("checking loan...");
        if(loan.getStatus()== LoanStatus.APPROVED){
            int id=loan.getTopUpLoanId();
            if(!(id==0)){
                logger.info("getting top up loan...");
                Loan topUpLoan=loanService.getLoan(id);
                loan.setTopUpLoanBalance(topUpLoan.getTotalDue());
                LoanTDto loanTDto=new LoanTDto();
                loanTDto.setLoanId(topUpLoan.getId());
                loanTDto.setAmount(topUpLoan.getTotalDue());
                loanTDto.setTransactionType(TransactionType.CASH);
                loanTDto.setAcctFrom(topUpLoan.getBorrower());
                logger.info("closing top up loan...");
                repay(loanTDto);

            }
            logger.info("setting transaction...");
        loanT.setLoanId(loanId);
        loanT.setDate(new Date());
        loanT.setStatus(TransactionStatus.PENDING);
        loanT.setTransactionType(transactionType);
        loanT.setUserName(userName);
        loanT.setAcctFrom("qciAcct");
        loanT.setAcctTo(loan.getBorrower());
            logger.info("setting transaction amount...");
        double totalCharge=getTotalCharge(loan);
        double amount=loan.getPrincipal()-totalCharge;
            loanT.setAmount(amount*-1);//********
            if(transactionType==TransactionType.MOBILE){
                logger.info("transacting mobile...");
                Profile profile =userService.getProfile(loan.getBorrower());
                MobilePayment mobilePayment=new MobilePayment();
                mobilePayment.setAmount(amount);//******
                mobilePayment.setFrom("qciAcct");
                mobilePayment.setTo(profile.getPerson().getMobile());
                transactionService.transactMobile(mobilePayment);
            }
            loanT.setStatus(TransactionStatus.SUCCESS);
            logger.info("updating loan...");
            loanService.changeStatus(loan, LoanStatus.OPEN);
            logger.info("transacting...");
            return transactionDao.save(loanT);

        }
        else{
            logger.info("invalid loan...");
            throw new InvalidValuesException("Only approved LOAN can be released");
        }


    }

    private double getTotalCharge(Loan loan) {
        logger.info("getting total charge...");
        return loan.getHandlingCharge()+loan.getInsuranceFee()
                +loan.getEarlyTopUpCharge()+loan.getExpressHandling()+loan.getTopUpLoanBalance();
    }


    @Override
    @Transactional
    public LoanT repayMobile(LoanPayDto loanPayDto) {
        logger.info("getting user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting loan...");
        Loan loan=loanService.getLoan(loanPayDto.getLoanId());
        Profile profile =userService.getProfile(userName);
        logger.info("checking loan...");
        if(loan.getStatus()==LoanStatus.OPEN || loan.getStatus()==LoanStatus.PASSED_MATURITY ){
            logger.info("checking user...");
        if(loan.getBorrower().equals(userName)){
            logger.info("comparing amounts...");
            if(loan.getTotalDue()<loanPayDto.getAmount()){
                logger.error("invalid amount...");
                throw new InvalidValuesException("invalid Amount...");
            }
            logger.info("preparing mobile transaction...");
            MobilePayment mobilePayment=new MobilePayment();
            String mobile= profile.getPerson().getMobile();
            mobilePayment.setTo("qciAcct");
            mobilePayment.setFrom(mobile);
            mobilePayment.setAmount(loanPayDto.getAmount());
            logger.info("transacting mobile...");
            transactionService.transactMobile(mobilePayment);
            logger.info("preparing transaction...");
           LoanT loanT=new LoanT();
           loanT.setUserName(userName);
           loanT.setAcctFrom(mobile);
           loanT.setTransactionType(TransactionType.MOBILE);
           loanT.setStatus(TransactionStatus.SUCCESS);
           loanT.setDate(new Date());
           loanT.setAmount(loanPayDto.getAmount());
           loanT.setLoanId(loanPayDto.getLoanId());
           loanT.setAcctTo("qciAcct");
            logger.info("updating loan...");
            loanService.repay(loanPayDto.getLoanId(), loanPayDto.getAmount());
            logger.info("saving transaction...");
            return transactionDao.save(loanT);

        }else {
            logger.error("invalid profile or loan...");
            throw new InvalidValuesException("invalid profile or loan...");
        }}
        logger.error("cant repay Loan...");
        throw new InvalidValuesException(" cant repay Loan ..."+ loan.getId());
    }

    @Override
    @Transactional
    public LoanT repay(LoanTDto loanTDto) {
        logger.info("getting admin user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting loan...");
        Loan loan=loanService.getLoan(loanTDto.getLoanId());
        logger.info("checking loan...");
        if(loan.getStatus()==LoanStatus.OPEN || loan.getStatus()==LoanStatus.PASSED_MATURITY ){
            logger.info("preparing loan transaction...");
        LoanT loanT=loanTConverter.dtoToEntity(loanTDto);
        loanT.setDate(new Date());
        loanT.setUserName(userName);
        loanT.setAcctFrom(loan.getBorrower());
        loanT.setAcctTo("QciAcct");
            logger.info("getting borrower profile...");
        Profile profile =userService.getProfile(loanTDto.getAcctFrom());
            logger.info("checking borrower and loan...");
        if(loan.getBorrower().equals(profile.getUserName())){
            logger.info("checking amount...");
            if(loan.getTotalDue()<loanT.getAmount()){
                logger.error("invalid Amount...");
                throw new InvalidValuesException("invalid Amount...");
            }

            loanT.setStatus(TransactionStatus.SUCCESS);
            logger.info("updating loan...");
        loanService.repay(loanTDto.getLoanId(), loanTDto.getAmount());
            logger.info("saving transaction...");
            return transactionDao.save(loanT);
        }else{
            logger.error("invalid Profile or Loan...");
            throw new InvalidValuesException("invalid Profile or Loan...");
        }}
        logger.error("invalid Loan...");
        throw new InvalidValuesException("invalid Loan...");
    }


    @Override
    @Transactional
    public LoanT payPenalty(LoanTDto loanTDto) {
        return null;
    }
}
