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
        logger.info("transacting...");

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
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();
            MembershipTDto membershipTDto = new MembershipTDto();
            membershipTDto.setUserName(user);
            membershipTDto.setTransactionType(TransactionType.CHEQUE);
            membershipTDto.setStatus(TransactionStatus.SUCCESS);
            membershipTDto.setCategory(TransactionCat.SAVING);
            membershipTDto.setAmount(amount);
            membershipTDto.setAcctTo("qciAcct");
            membershipTDto.setAcctFrom(userName);
            membershipTDto.setYear(2020);
            membershipTDto.setDate(new Date());
            saveMembershipT(membershipTDto);
        }

    }

}
