package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.account.model.UserAccount;
import com.qualitychemicals.qciss.account.model.Wallet;
import com.qualitychemicals.qciss.account.service.MembershipAccountService;
import com.qualitychemicals.qciss.account.service.WalletService;
import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.saccoData.appConfig.AppConfigReader;
import com.qualitychemicals.qciss.saccoData.service.MembershipService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
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
import org.springframework.transaction.annotation.Isolation;
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
    @Autowired MyUserDetailsService myUserDetailsService;
    @Autowired AccountService accountService;
    @Autowired RestTemplate restTemplate;
    @Autowired WalletService walletService;
    @Autowired MembershipAccountService membershipAccountService;
    @Autowired MembershipService membershipService;
    @Autowired
    AppConfigReader appConfigReader;
    private final Logger logger= LoggerFactory.getLogger(MembershipTServImpl.class);

    @Override
    public MembershipTDto payMembership(MembershipTDto membershipTDto) {

       return null;
    }

    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE)
    public MembershipTDto payMembership(double amount) {
        String user =myUserDetailsService.currentUser();
        Wallet wallet =walletService.getWallet("WAL"+user);
        if(amount>wallet.getAmount()){
            throw new InvalidValuesException("low wallet bal");
        }else{
            MembershipTDto membershipTDto =new MembershipTDto();
            membershipTDto.setYear(2021);
            membershipTDto.setAccount("MEM"+user);
            membershipTDto.setAmount(amount);
            membershipTDto.setCreationDateTime(new Date());
            membershipTDto.setDate(new Date());
            membershipTDto.setNarrative("paid membership fee");
            membershipTDto.setTransactionType("membership");
            membershipTDto.setStatus(TransactionStatus.PENDING);
            membershipTDto.setUserName(user);
            membershipTDto.setWallet(wallet.getAccountRef());
            MembershipTDto response =saveMembershipT(membershipTDto).getBody();
            assert response != null;
            if(response.getStatus().equals(TransactionStatus.SUCCESS)){
                UserAccount userAccount =new UserAccount();
                userAccount.setLastTransaction(response.getId());
                logger.info(("updating wallet..."));
                userAccount.setAmount(amount*-1);
                userAccount.setAccountRef(wallet.getAccountRef());
                walletService.transact(userAccount);
                logger.info(("updating membership..."));
                userAccount.setAccountRef("MEM"+user);
                userAccount.setAmount(amount);
                membershipAccountService.transact(userAccount);

                logger.info(("updating sacco membership account..."));
                membershipService.updateMembership(amount);

            }
            return response;

        }

    }

    @Override
    public MembershipTransactionsDto myRecent() {
        String user =myUserDetailsService.currentUser();
        String wallet ="WAL"+user;
        return last5ByWallet(wallet);
    }

    @Override
    public MembershipTransactionsDto myAll() {
        String user =myUserDetailsService.currentUser();
        String wallet ="WAL"+user;
        return allByWallet(wallet);
    }

    @Override
    public MembershipTransactionsDto allByWallet(String wallet) {
        String url ="http://localhost:8082/transaction/membership/allByWallet/";
        try {
            return restTemplate.getForObject(
                    url + wallet, MembershipTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public MembershipTransactionsDto last5ByWallet(String wallet) {
        String url ="http://localhost:8082/transaction/membership/recentByWallet/";
        try {
            return restTemplate.getForObject(
                    url + wallet, MembershipTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
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





    /*public void initialMembership(double amount, String userName) {
        if(amount>0) {
            logger.info("getting user... ");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();
            logger.info("user "+user);
            MembershipTDto membershipTDto = new MembershipTDto();
            membershipTDto.setUserName(user);
            membershipTDto.setStatus(TransactionStatus.SUCCESS);
            membershipTDto.setAmount(amount);
            membershipTDto.setYear(2021);
            membershipTDto.setDate(new Date());
            logger.info("saving membershipT");
            saveMembershipT(membershipTDto);
        }
        logger.info("no membership fee");
    }*/

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
