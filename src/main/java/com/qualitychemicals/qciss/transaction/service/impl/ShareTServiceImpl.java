package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.saccoData.appConfig.AppConfigReader;
import com.qualitychemicals.qciss.transaction.dto.*;
import com.qualitychemicals.qciss.transaction.service.ShareTService;
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
public class ShareTServiceImpl implements ShareTService {

    @Autowired
    TransactionService transactionService;
    @Autowired AccountService accountService;
    @Autowired RestTemplate restTemplate;
    @Autowired
    AppConfigReader appConfigReader;
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
        ResponseEntity<ShareTDto> response=saveShareT(shareTDto);
        return response.getBody();
    }

    private ResponseEntity<ShareTDto> saveShareT(ShareTDto shareTDto) {
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
        ResponseEntity<ShareTDto> response=saveShareT(shareTDto);
        return response.getBody();
    }

    public void initialShares(double qtty, String userName) {
        if(qtty>0) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String user = auth.getName();
            double unitCost =20000;
            ShareTDto shareTDto = new ShareTDto();
            shareTDto.setUserName(user);
            shareTDto.setTransactionType(TransactionType.CHEQUE);
            shareTDto.setStatus(TransactionStatus.SUCCESS);
            shareTDto.setCategory(TransactionCat.MEMBERSHIP);
            shareTDto.setAmount(unitCost*qtty);
            shareTDto.setAcctTo(appConfigReader.getSaccoAccount());
            shareTDto.setAcctFrom(userName);
            shareTDto.setUnitCost(unitCost);
            shareTDto.setShares(qtty);
            shareTDto.setDate(new Date());
            saveShareT(shareTDto);
        }

    }

    @Override
    public SharesTransactionsDto shareTransactions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        try {
            return restTemplate.getForObject(
                    "http://localhost:8082/transaction/shares/shareTransactions/"+user, SharesTransactionsDto.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public double totalShares(Date date) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String myDate=jsn.format(date);
        final String uri="http://localhost:8082/transaction/shares/totalShares/" + myDate;
        try {

            return restTemplate.getForObject(uri, Double.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }

    @Override
    public double totalShares(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/shares/totalShares/"+dateFrom+"/"+dateTo;
        try {

            return restTemplate.getForObject(uri, Double.class);
        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }

    }

    @Override
    public DateTransactions dateShares(Date date1, Date date2) {
        SimpleDateFormat jsn = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom=jsn.format(date1);
        String dateTo=jsn.format(date2);
        final String uri="http://localhost:8082/transaction/shares/dateShares/"+dateFrom+"/"+dateTo;
        try {

            return restTemplate.getForObject(uri, DateTransactions.class);

        }catch (RestClientException e) {
            throw new ResourceNotFoundException("Transaction Service down " );
        }
    }
}
