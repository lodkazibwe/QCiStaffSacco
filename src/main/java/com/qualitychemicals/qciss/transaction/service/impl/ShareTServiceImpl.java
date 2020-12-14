package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.transaction.dto.ShareTDto;
import com.qualitychemicals.qciss.transaction.dto.TransactionCat;
import com.qualitychemicals.qciss.transaction.dto.TransactionDto;
import com.qualitychemicals.qciss.transaction.service.ShareTService;
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
public class ShareTServiceImpl implements ShareTService {

    @Autowired
    TransactionService transactionService;
    @Autowired AccountService accountService;
    @Autowired RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(ShareTServiceImpl.class);
    @Override
    public ShareTDto mobileShares(double amount) {
        logger.info("transacting...");
        TransactionDto transaction=transactionService.receiveMobileMoney(amount, TransactionCat.SHARE);
        logger.info("preparing transaction...");
        ShareTDto shareTDto=new ShareTDto();
        double shareCost=20000;
        double shares=amount/shareCost;
        shareTDto.setShares(shares);
        shareTDto.setUnitCost(shareCost);
        shareTDto.setTransactionType(transaction.getTransactionType());
        shareTDto.setAcctTo(transaction.getAcctTo());
        shareTDto.setAcctFrom(transaction.getAcctFrom());
        shareTDto.setUserName(transaction.getUserName());
        shareTDto.setStatus(transaction.getStatus());
        shareTDto.setDate(transaction.getDate());
        shareTDto.setAmount(transaction.getAmount());
        shareTDto.setId(transaction.getId());
        logger.info("updating shares...");
        accountService.updateShares(amount/shareCost, transaction.getUserName());
        logger.info("saving transaction...");
        ResponseEntity<ShareTDto> response=saveSavingT(shareTDto);
        return response.getBody();
    }

    private ResponseEntity<ShareTDto> saveSavingT(ShareTDto shareTDto) {
        logger.info("transacting...");

        try {
            logger.info("connecting to payment service...");
            final String uri="http://localhost:8082/transaction/shares/save";
            HttpEntity<ShareTDto> request = new HttpEntity<>(shareTDto);
            return restTemplate.exchange(uri, HttpMethod.POST,request,ShareTDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }

    @Transactional
    @Override
    public ShareTDto systemShares(ShareTDto shareTDto) {
        logger.info("updating shares...");
        double shareCost=20000;
        accountService.updateShares(shareTDto.getAmount()/shareCost, shareTDto.getAcctFrom());
        logger.info("saving transaction...");
        ResponseEntity<ShareTDto> response=saveSavingT(shareTDto);
        return response.getBody();
    }
}
