package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.saccoData.dao.SavingDao;
import com.qualitychemicals.qciss.saccoData.model.LoanAccount;
import com.qualitychemicals.qciss.saccoData.model.Saving;
import com.qualitychemicals.qciss.saccoData.service.LoanAccountService;
import com.qualitychemicals.qciss.saccoData.service.SaccoAccountService;
import com.qualitychemicals.qciss.saccoData.service.SavingService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.dto.SavingTDto;
import com.qualitychemicals.qciss.transaction.dto.TransactionStatus;
import com.qualitychemicals.qciss.transaction.dto.TransactionType;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
import com.qualitychemicals.qciss.transaction.service.SavingTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Date;

@Service
public class SavingServiceImpl implements SavingService {
    @Autowired SavingDao savingDao;
    @Autowired
    SaccoAccountService saccoAccountService;
    @Autowired
    LoanAccountService loanAccountService;
    @Autowired
    LoanTService loanTService;
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired SavingTService savingTService;

    private final Logger logger = LoggerFactory.getLogger(SavingServiceImpl.class);

    @Override
    public Saving addSaving(Saving saving) {
        boolean bool =saccoAccountService.existsByName("SAVING");
        if(bool){
            throw new ResourceAccessException("account already exists");
        }
        saving.setName("SAVING");
        return savingDao.save(saving);
    }

    @Override
    public Saving updateSaving(double amount) {
        Saving saving =getSavingInfo();
        saving.setAmount(amount+saving.getAmount());
        saving.setDaySaving(amount+saving.getDaySaving());
        return savingDao.save(saving);
    }

    @Override
    public Saving resetSaving(double amount) {
        Saving saving =getSavingInfo();
        saving.setDaySaving(saving.getDaySaving()-amount);
        return savingDao.save(saving);
    }

    @Override
    public Saving getSavingInfo() {
        Saving saving =savingDao.findByName("SAVING");
        if (saving == null) {
            throw new ResourceAccessException("admin sav_account not found");
        }
        return saving;
    }

    @Override
    public Saving sendToLoanAccount(double amount) {
        logger.info("getting saving account...");
        Saving saving =getSavingInfo();
        if(saving.getAmount()<amount){
            throw  new InvalidValuesException("invalid amount...");
        }
        String userName =myUserDetailsService.currentUser();
        logger.info("updating sacco loanAccount...");
        LoanAccount loanAccount =new LoanAccount();
        loanAccount.setAmount(amount);
        loanAccountService.updateLoanAccount(loanAccount);
        logger.info("Transactions loanT and savingT...");
        LoanTDto loanTDto=new LoanTDto();
        loanTDto.setTransactionType(TransactionType.INTERNAL);
        loanTDto.setAccount(loanAccount.getName());
        loanTDto.setCreationDateTime(new Date());
        loanTDto.setWallet(saving.getName());
        loanTDto.setNarrative("transfer saving to loanAccount");
        loanTDto.setStatus(TransactionStatus.PENDING);
        loanTDto.setDate(new Date());
        loanTDto.setAmount(amount);
        loanTDto.setUserName(userName);
        loanTService.saveLoanTransaction(loanTDto);
        logger.info("saving saving transaction...");
        SavingTDto savingTDto =new SavingTDto();
        savingTDto.setWallet(loanAccount.getName());
        savingTDto.setTransactionType(TransactionType.INTERNAL);
        savingTDto.setAccount(saving.getName());
        savingTDto.setNarrative("transfer saving to loanAccount");
        savingTDto.setCreationDateTime(new Date());
        savingTDto.setAccountId(saving.getId());
        savingTDto.setUserName(userName);
        savingTDto.setStatus(TransactionStatus.PENDING);
        savingTDto.setAmount(amount*-1);
        savingTDto.setDate(new Date());
        savingTService.saveSavingTransaction(savingTDto);

        logger.info("updating saving account...");
        saving.setAmount(saving.getAmount()-amount);
        return savingDao.save(saving);
    }

}
