package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.service.LoanService;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.dto.*;
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

import java.net.ConnectException;
import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired UserService userService;
    @Autowired LoanService loanService;
    @Autowired RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Override
    @Transactional
    public TransactionDto receiveMobileMoney(double amount, TransactionCat category) {

        if(amount>=5000){
            logger.info("getting user");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName = auth.getName();
            Profile profile = userService.getProfile(userName);
            logger.info("setting payment...");
            MobilePayment mobilePayment = new MobilePayment();
            String mobile = profile.getPerson().getMobile();
            mobilePayment.setTo("qciAcct");
            mobilePayment.setFrom(mobile);
            mobilePayment.setAmount(amount);
            transactMobile(mobilePayment);

            logger.info("setting transaction...");
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setAmount(amount);
            transactionDto.setDate(new Date());
            transactionDto.setStatus(TransactionStatus.SUCCESS);
            transactionDto.setTransactionType(TransactionType.MOBILE);
            transactionDto.setUserName(userName);
            transactionDto.setAcctFrom(mobile);
            transactionDto.setAcctTo("qciAcct");
            transactionDto.setCategory(category);
            return transactionDto;
        }throw new InvalidValuesException("send 5000 and above");

    }

    public  MobilePayment transactMobile(MobilePayment mobilePayment) {
        logger.info("transacting...");
        try {
            logger.info("connecting to payment service...");
            final String uri = "http://localhost:8082/transaction/mobilePayment";
            HttpEntity<MobilePayment> request = new HttpEntity<>(mobilePayment);
            ResponseEntity<MobilePayment> response = restTemplate.exchange(uri, HttpMethod.POST, request, MobilePayment.class);
            HttpStatus httpStatus = response.getStatusCode();
            if (httpStatus == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new InvalidValuesException("external application error " + httpStatus);
            }
        }catch (RestClientException e) {
            if (e.getCause() instanceof ConnectException) {

                throw new ResourceNotFoundException("Transaction Service down:ConnectException");
            }
            throw new ResourceNotFoundException("external application down " );
        }


    }

    @Override
    @Transactional
    public LoanTransactionsDto loanTransactions(int loanId) {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/loanTransactions/" + loanId, LoanTransactionsDto.class);
        }catch (RestClientException e) {
                throw new ResourceNotFoundException("Transaction Service down " );
            }
    }

    @Override
    @Transactional
    public UserTransactionsDto userTransactions(String userName) {

        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/userTransactions/"+userName, UserTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }

    @Override
    public UserTransactionsDto myTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        return userTransactions(userName);
    }

    @Override
    public LoanTransactionsDto myLoanTransactions(int loanId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Loan loan=loanService.getLoan(loanId);
        if(userName.equals(loan.getBorrower())){
            return loanTransactions(loanId);
        }
        throw new InvalidValuesException("invalid loan Id " );
    }

    @Override
    public AllTransactions allTransactions() {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/getAll", AllTransactions.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public LoanTransactionsDto loanTransactions() {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/loan/getAll", LoanTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public SavingsTransactionsDto savingTransactions() {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/saving/getAll", SavingsTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public MembershipTransactionsDto membershipTransactions() {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/membership/getAll", MembershipTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public SharesTransactionsDto shareTransactions() {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/shares/getAll", SharesTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public AllTransactions allByType(TransactionType transactionType) {
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/getAll" +transactionType, AllTransactions.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    /*private ResponseEntity<TransactionDto> saveTransaction(TransactionDto transactionDto) {
        logger.info("transacting...");

            logger.info("connecting to payment service...");
            final String uri="http://localhost:8082/transaction/save";
            HttpEntity<TransactionDto> request = new HttpEntity<>(transactionDto);
            return restTemplate.exchange(uri, HttpMethod.POST,request,TransactionDto.class);

    }*/

    }
