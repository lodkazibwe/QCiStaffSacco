package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.dto.*;
import com.qualitychemicals.qciss.transaction.service.MembershipTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MembershipTServImpl implements MembershipTService {
    @Autowired
    TransactionService transactionService;
    @Autowired UserService userService;
    @Autowired AccountService accountService;
    @Autowired RestTemplate restTemplate;
    private final Logger logger= LoggerFactory.getLogger(MembershipTServImpl.class);
    @Override
    public MembershipTDto payMembership(MembershipTDto membershipTDto) {

       return null;
    }

    @Override
    @Transactional
    public MembershipTDto payMembership(double amount) {
        TransactionDto transaction=transactionService.receiveMobileMoney(amount, TransactionCat.MEMBERSHIP);
        logger.info("preparing Transaction");
        MembershipTDto membershipTDto=new MembershipTDto();
        membershipTDto.setYear(2021);
        membershipTDto.setTransactionType(transaction.getTransactionType());
        membershipTDto.setAcctTo(transaction.getAcctTo());
        membershipTDto.setAcctFrom(transaction.getAcctFrom());
        membershipTDto.setUserName(transaction.getUserName());
        membershipTDto.setStatus(transaction.getStatus());
        membershipTDto.setDate(transaction.getDate());
        membershipTDto.setAmount(transaction.getAmount());
        membershipTDto.setId(transaction.getId());
        logger.info("updating membership fee...");
        accountService.updateMembership(amount, membershipTDto.getUserName());
        logger.info("savingTransaction");
        ResponseEntity<MembershipTDto> response=saveMembershipT(membershipTDto);
        return response.getBody();
    }

    private ResponseEntity<MembershipTDto> saveMembershipT(MembershipTDto membershipTDto) {
        logger.info("transacting Membershipfee...");

        try {
            logger.info("connecting to payment service...");
            final String uri="http://localhost:8082/transaction/membership/save";
            HttpEntity<MembershipTDto> request = new HttpEntity<>(membershipTDto);
            return restTemplate.exchange(uri, HttpMethod.POST,request,MembershipTDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }

    public void initialMembership(double amount, String userName) {
        if(amount>0) {
            logger.info("getting user... ");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();
            logger.info("user "+user);
            MembershipTDto membershipTDto = new MembershipTDto();
            membershipTDto.setUserName(user);
            membershipTDto.setTransactionType(TransactionType.CHEQUE);
            membershipTDto.setStatus(TransactionStatus.SUCCESS);
            membershipTDto.setCategory(TransactionCat.MEMBERSHIP);
            membershipTDto.setAmount(amount);
            membershipTDto.setAcctTo("qciAcct");
            membershipTDto.setAcctFrom(userName);
            membershipTDto.setYear(2021);
            membershipTDto.setDate(new Date());
            logger.info("saving membershipT");
            saveMembershipT(membershipTDto);
        }
        logger.info("no membership fee");
    }

    @Override
    public MembershipTransactionsDto membershipTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/membership/membershipTrans/" +user, MembershipTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public double totalMembership(Date date) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String myDate=jsn.format(date);
        final String uri="http://localhost:8082/transaction/membership/totalMembership/" + myDate;
        try {

            return restTemplate.getForObject(uri, Double.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public double totalMembership(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/membership/totalMembership/"+dateFrom+"/"+dateTo;
        try {

            return restTemplate.getForObject(uri, Double.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public DateTransactions dateMembership(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/membership/dateMembership/"+dateFrom+"/"+dateTo;
        try {

            return restTemplate.getForObject(uri, DateTransactions.class);

        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }
}
