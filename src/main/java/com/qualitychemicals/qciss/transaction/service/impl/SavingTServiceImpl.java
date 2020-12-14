package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.transaction.dto.SavingTDto;
import com.qualitychemicals.qciss.transaction.dto.SavingType;
import com.qualitychemicals.qciss.transaction.dto.TransactionCat;
import com.qualitychemicals.qciss.transaction.dto.TransactionDto;
import com.qualitychemicals.qciss.transaction.service.SavingTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


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
}
