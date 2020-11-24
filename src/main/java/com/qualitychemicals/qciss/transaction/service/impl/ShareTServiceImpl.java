package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.profile.service.AccountService;
import com.qualitychemicals.qciss.transaction.dao.TransactionDao;
import com.qualitychemicals.qciss.transaction.model.ShareT;
import com.qualitychemicals.qciss.transaction.model.Transaction;
import com.qualitychemicals.qciss.transaction.service.ShareTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareTServiceImpl implements ShareTService {

    @Autowired
    TransactionService transactionService;
    @Autowired AccountService accountService;
    @Autowired
    TransactionDao transactionDao;
    private final Logger logger = LoggerFactory.getLogger(ShareTServiceImpl.class);
    @Override
    public ShareT mobileShares(double amount) {
        logger.info("transacting...");
        Transaction transaction=transactionService.receiveMobileMoney(amount);
        logger.info("preparing transaction...");
        ShareT shareT=new ShareT();
        double shareCost=20000;
        double shares=amount/shareCost;
        shareT.setShares(shares);
        shareT.setUnitCost(shareCost);
        shareT.setTransactionType(transaction.getTransactionType());
        shareT.setAcctTo(transaction.getAcctTo());
        shareT.setAcctFrom(transaction.getAcctFrom());
        shareT.setUserName(transaction.getUserName());
        shareT.setStatus(transaction.getStatus());
        shareT.setDate(transaction.getDate());
        shareT.setAmount(transaction.getAmount());
        shareT.setId(transaction.getId());
        logger.info("updating shares...");
        accountService.updateShares(amount/shareCost, transaction.getUserName());
        logger.info("saving transaction...");
        return transactionDao.save(shareT);
    }
}
