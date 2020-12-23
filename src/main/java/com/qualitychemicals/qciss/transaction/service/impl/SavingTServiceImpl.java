package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.dto.*;
import com.qualitychemicals.qciss.transaction.service.SavingTService;
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
public class SavingTServiceImpl implements SavingTService {
    @Autowired UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @Autowired RestTemplate restTemplate;

    private final Logger logger = LoggerFactory.getLogger(SavingTServiceImpl.class);

    @Override
    @Transactional
    public SavingTDto mobileSaving(double amount) {
        logger.info("transacting...");
        TransactionDto transaction=transactionService.receiveMobileMoney(amount, TransactionCat.SAVING);
        logger.info("setting transaction...");
        SavingTDto savingTDto=new SavingTDto();
        savingTDto.setSavingType(SavingType.DIRECT);
        savingTDto.setTransactionType(transaction.getTransactionType());
        savingTDto.setAcctTo(transaction.getAcctTo());
        savingTDto.setAcctFrom(transaction.getAcctFrom());
        savingTDto.setUserName(transaction.getUserName());
        savingTDto.setStatus(transaction.getStatus());
        savingTDto.setDate(transaction.getDate());
        savingTDto.setAmount(transaction.getAmount());
        savingTDto.setId(transaction.getId());
        logger.info("updating saving...");
        accountService.updateSaving(amount, transaction.getUserName());
        logger.info("saving transaction...");
        ResponseEntity<SavingTDto> response=saveSavingT(savingTDto);
        return response.getBody();
    }



    private ResponseEntity<SavingTDto> saveSavingT(SavingTDto savingTDto) {
        logger.info("transacting...");
        try {
            logger.info("connecting to payment service...");
            final String uri="http://localhost:8082/transaction/saving/save";
            HttpEntity<SavingTDto> request = new HttpEntity<>(savingTDto);
            return restTemplate.exchange(uri, HttpMethod.POST,request,SavingTDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }

    @Transactional
    @Override
    public SavingTDto systemSaving(SavingTDto savingTDto) {
        logger.info("updating saving...");
        accountService.updateSaving(savingTDto.getAmount(), savingTDto.getAcctFrom());
        logger.info("saving transaction...");
        ResponseEntity<SavingTDto> response=saveSavingT(savingTDto);
        return response.getBody();
    }

    public void initialSaving(double amount, String userName) {
        if(amount>0) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();
            SavingTDto savingTDto = new SavingTDto();
            savingTDto.setUserName(user);
            savingTDto.setTransactionType(TransactionType.CHEQUE);
            savingTDto.setStatus(TransactionStatus.SUCCESS);
            savingTDto.setCategory(TransactionCat.SAVING);
            savingTDto.setAmount(amount);
            savingTDto.setAcctTo("qciAcct");
            savingTDto.setAcctFrom(userName);
            savingTDto.setSavingType(SavingType.DIRECT);
            savingTDto.setDate(new Date());
            saveSavingT(savingTDto);
        }

    }

    @Override
    public double totalSaving(Date date) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String myDate=jsn.format(date);
        final String uri="http://localhost:8082/transaction/saving/totalSaving/" + myDate;
        try {

          final Double total= restTemplate.getForObject(uri, Double.class);
            return total;
      }catch (RestClientException e) {
          throw new ResourceNotFoundException("Transaction Service down " );
      }

    }

    @Override
    public double totalSaving(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/saving/totalSaving/"+dateFrom+"/"+dateTo;
        try {

            final Double total= restTemplate.getForObject(uri, Double.class);
            return total;
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public DateTransactions dateSaving(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/saving/dateSaving/2020-12-2/2020-12-4";
        try {

            DateTransactions dateTransactions= restTemplate.getForObject(uri, DateTransactions.class);
            return dateTransactions;
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }


}
