package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.saccoData.dao.ShareDao;
import com.qualitychemicals.qciss.saccoData.model.LoanAccount;
import com.qualitychemicals.qciss.saccoData.model.Share;
import com.qualitychemicals.qciss.saccoData.service.LoanAccountService;
import com.qualitychemicals.qciss.saccoData.service.SaccoAccountService;
import com.qualitychemicals.qciss.saccoData.service.ShareService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.dto.ShareTDto;
import com.qualitychemicals.qciss.transaction.dto.TransactionStatus;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
import com.qualitychemicals.qciss.transaction.service.ShareTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Date;

@Service
public class ShareServiceImpl implements ShareService {
    @Autowired ShareDao shareDao;
    @Autowired
    SaccoAccountService saccoAccountService;
    @Autowired
    LoanAccountService loanAccountService;
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    LoanTService loanTService;
    @Autowired ShareTService shareTService;

    private final Logger logger = LoggerFactory.getLogger(ShareServiceImpl.class);

    @Override
    public Share addShare(Share share) {
        boolean bool =saccoAccountService.existsByName("SHARES");
        if(bool){
            throw new ResourceAccessException("account already exists");
        }
        return shareDao.save(share);
    }

    @Override
    public Share updateShare(Share update) {
        Share share =getShareInfo();
        share.setSharesAvailable(update.getSharesAvailable()+share.getSharesAvailable());
        share.setSharesSold(update.getSharesSold()+share.getSharesSold());
        share.setAmount(update.getAmount()+share.getAmount());
        share.setDayShares(update.getDayShares()+share.getDayShares());
        share.setDayShareAmount(update.getDayShareAmount()+share.getDayShareAmount());
        return shareDao.save(share);
    }

    @Override
    public Share resetShare(Share update) {
        Share share =getShareInfo();
        share.setDayShares(share.getDayShares()-update.getDayShares());
        share.setDayShareAmount(share.getDayShareAmount()-update.getDayShareAmount());
        return shareDao.save(share);
    }

    @Override
    public Share getShareInfo() {
        Share share =shareDao.findByName("SHARES");
        if (share == null) {
            throw new ResourceAccessException("admin sha_account not found");
        }
        return share;
    }

    @Override
    public Share sendToLoanAccount(double amount) {
        logger.info("getting share account...");
        Share share =getShareInfo();
        if(share.getAmount()<amount){
        throw  new InvalidValuesException("invalid amount...");
            }
        String userName =myUserDetailsService.currentUser();
        logger.info("updating sacco loanAccount...");
        LoanAccount loanAccount =new LoanAccount();
        loanAccount.setAmount(amount);
        loanAccountService.updateLoanAccount(loanAccount);

        logger.info("Transactions loanT and shareT...");
        LoanTDto loanTDto=new LoanTDto();
        loanTDto.setTransactionType("loan");
        loanTDto.setAccount(loanAccount.getName());
        loanTDto.setCreationDateTime(new Date());
        loanTDto.setWallet(share.getName());
        loanTDto.setNarrative("transfer shareAccount to loanAccount");
        loanTDto.setStatus(TransactionStatus.PENDING);
        loanTDto.setDate(new Date());
        loanTDto.setAmount(amount);
        loanTDto.setUserName(userName);
        loanTService.saveLoanTransaction(loanTDto);

        logger.info("saving share transaction...");
        ShareTDto shareTDto =new ShareTDto();
        shareTDto.setWallet(loanAccount.getName());
        shareTDto.setTransactionType("share");
        shareTDto.setNarrative("transfer shareAccount to loanAccount");
        shareTDto.setAccount(share.getName());
        shareTDto.setShareValue(share.getShareValue());
        shareTDto.setAmount(amount*-1);
        shareTDto.setCreationDateTime(new Date());
        shareTDto.setDate(new Date());
        shareTDto.setStatus(TransactionStatus.PENDING);
        shareTDto.setUserName(userName);
        shareTService.saveShareTransaction(shareTDto);

        logger.info("updating share account...");
        share.setAmount(share.getAmount()-amount);
        return shareDao.save(share);

    }
}
