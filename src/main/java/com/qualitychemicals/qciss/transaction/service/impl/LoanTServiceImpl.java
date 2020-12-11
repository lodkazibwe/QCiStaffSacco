package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.model.LoanStatus;
import com.qualitychemicals.qciss.loan.service.LoanService;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.dto.*;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class LoanTServiceImpl implements LoanTService {
    @Autowired LoanService loanService;
    @Autowired UserService userService;
    @Autowired
    TransactionService transactionService;
    @Autowired RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(LoanTServiceImpl.class);

    @Override
    @Transactional
    public LoanTDto release(LoanPayDto loanPayDto) {

        logger.info("getting loan...");
        Loan loan=loanService.getLoan(loanPayDto.getLoanId());
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
            LoanTDto loanTDto=new LoanTDto();
            loanTDto.setLoanId(loanPayDto.getLoanId());
            loanTDto.setDate(new Date());
            loanTDto.setStatus(TransactionStatus.PENDING);
            loanTDto.setCategory(TransactionCat.LOAN);
            loanTDto.setTransactionType(loanPayDto.getTransactionType());
            loanTDto.setUserName(userName);
            loanTDto.setAcctFrom("qciAcct");
            loanTDto.setAcctTo(loan.getBorrower());
            logger.info("setting transaction amount...");
        double totalCharge=getTotalCharge(loan);
        double amount=loan.getPrincipal()-totalCharge;
            loanTDto.setAmount(amount*-1);
            if(loanPayDto.getTransactionType()==TransactionType.MOBILE){
                logger.info("transacting mobile...");
                Profile profile =userService.getProfile(loan.getBorrower());
                MobilePayment mobilePayment=new MobilePayment();
                mobilePayment.setAmount(amount);//******
                mobilePayment.setFrom("qciAcct");
                mobilePayment.setTo(profile.getPerson().getMobile());
                transactionService.transactMobile(mobilePayment);
            }
            ResponseEntity<LoanTDto> response=saveLoanT(loanTDto);
            HttpStatus httpStatus=response.getStatusCode();
            LoanTDto loanTDt=response.getBody();
            if(httpStatus==HttpStatus.OK){
                logger.info("updating loan...");
                loanService.changeStatus(loan, LoanStatus.OPEN);
                assert loanTDt != null;
                return loanTDt;
            }else{
                throw new InvalidValuesException("external application error "+httpStatus);
            }

        }
        else{
            logger.info("invalid loan...");
            throw new InvalidValuesException("Only approved LOAN can be released");
        }


    }

    private ResponseEntity<LoanTDto> saveLoanT(LoanTDto loanTDto) {
        logger.info("transacting...");

        try {
            logger.info("connecting to payment service...");
            final String uri="http://localhost:8082/transaction/loan/release";
            HttpEntity<LoanTDto> request = new HttpEntity<>(loanTDto);
            return restTemplate.exchange(uri, HttpMethod.POST,request,LoanTDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }

    private double getTotalCharge(Loan loan) {
        logger.info("getting total charge...");
        return loan.getHandlingCharge()+loan.getInsuranceFee()
                +loan.getEarlyTopUpCharge()+loan.getExpressHandling()+loan.getTopUpLoanBalance();
    }


    @Override
    @Transactional
    public LoanTDto repayMobile(LoanPayDto loanPayDto) {
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
           //LoanT loanT=new LoanT();
            LoanTDto loanTDto=new LoanTDto();
            loanTDto.setUserName(userName);
            loanTDto.setAcctFrom(mobile);
            loanTDto.setTransactionType(TransactionType.MOBILE);
            loanTDto.setStatus(TransactionStatus.SUCCESS);
            loanTDto.setCategory(TransactionCat.LOAN);
            loanTDto.setDate(new Date());
            loanTDto.setAmount(loanPayDto.getAmount());
            loanTDto.setLoanId(loanPayDto.getLoanId());
            loanTDto.setAcctTo("qciAcct");
            logger.info("updating loan...");
            loanService.repay(loanPayDto.getLoanId(), loanPayDto.getAmount());
            logger.info("saving transaction...");
            ResponseEntity<LoanTDto> response=saveLoanT(loanTDto);
            return response.getBody();

        }else {
            logger.error("invalid profile or loan...");
            throw new InvalidValuesException("invalid profile or loan...");
        }}
        logger.error("cant repay Loan...");
        throw new InvalidValuesException(" cant repay Loan ..."+ loan.getId());
    }

    @Override
    @Transactional
    public LoanTDto repay(LoanTDto loanTDto) {
        logger.info("getting admin user...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting loan...");
        Loan loan=loanService.getLoan(loanTDto.getLoanId());
        logger.info("checking loan...");
        if(loan.getStatus()==LoanStatus.OPEN || loan.getStatus()==LoanStatus.PASSED_MATURITY ){
            logger.info("preparing loan transaction...");
       // LoanT loanT=loanTConverter.dtoToEntity(loanTDto);
        loanTDto.setDate(new Date());
            loanTDto.setUserName(userName);
            loanTDto.setAcctFrom(loan.getBorrower());
            loanTDto.setCategory(TransactionCat.LOAN);
            loanTDto.setAcctTo("QciAcct");
            logger.info("getting borrower profile...");
        Profile profile =userService.getProfile(loanTDto.getAcctFrom());
            logger.info("checking borrower and loan...");
        if(loan.getBorrower().equals(profile.getUserName())){
            logger.info("checking amount...");
            if(loan.getTotalDue()<loanTDto.getAmount()){
                logger.error("invalid Amount...");
                throw new InvalidValuesException("invalid Amount...");
            }
            logger.info("transacting...");
            ResponseEntity<LoanTDto> response=saveLoanT(loanTDto);
            HttpStatus httpStatus=response.getStatusCode();
            LoanTDto loanTDt=response.getBody();
            if(httpStatus==HttpStatus.OK){
                logger.info("updating loan...");
                loanService.repay(loanTDto.getLoanId(), loanTDto.getAmount());
                assert loanTDt != null;
                return loanTDt;
            }else{
                throw new InvalidValuesException("external application error "+httpStatus);
            }

        }else{
            logger.error("invalid Profile or Loan...");
            throw new InvalidValuesException("invalid Profile or Loan...");
        }}
        logger.error("invalid Loan...");
        throw new InvalidValuesException("invalid Loan...");
    }


    @Override
    @Transactional
    public LoanTDto payPenalty(LoanTDto loanTDto) {
        return null;
    }
}

