package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.saccoData.dao.MembershipDao;
import com.qualitychemicals.qciss.saccoData.model.LoanAccount;
import com.qualitychemicals.qciss.saccoData.model.Membership;
import com.qualitychemicals.qciss.saccoData.service.LoanAccountService;
import com.qualitychemicals.qciss.saccoData.service.MembershipService;
import com.qualitychemicals.qciss.saccoData.service.SaccoAccountService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.dto.MembershipTDto;
import com.qualitychemicals.qciss.transaction.dto.TransactionStatus;
import com.qualitychemicals.qciss.transaction.dto.TransactionType;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
import com.qualitychemicals.qciss.transaction.service.MembershipTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import java.util.Date;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    SaccoAccountService saccoAccountService;
    @Autowired
    LoanAccountService loanAccountService;
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    LoanTService loanTService;
    @Autowired MembershipTService membershipTService;

    @Autowired
    MembershipDao membershipDao;
    private final Logger logger = LoggerFactory.getLogger(MembershipServiceImpl.class);



    @Override
    public Membership addMembership(Membership membership) {
        boolean bool =saccoAccountService.existsByName("MEMBERSHIP");
        if(bool){
            throw new ResourceAccessException("account already exists");
        }
        membership.setName("MEMBERSHIP");
        return membershipDao.save(membership);
    }

    @Override
    public Membership updateMembership(double amount) {
        Membership membership =getMembership();
        membership.setAmount(amount+membership.getAmount());
        membership.setDayMembership(amount+membership.getDayMembership());
        return membershipDao.save(membership);
    }

    @Override
    public Membership resetMembership(double amount) {
        Membership membership =getMembership();
        membership.setDayMembership(membership.getDayMembership()-amount);
        return membershipDao.save(membership);
    }

    @Override
    public Membership getMembership() {
            Membership membership =membershipDao.findByName("MEMBERSHIP");
        if (membership == null) {
            throw new ResourceAccessException("admin mem_account not found");
        }
        return membership;
    }

    @Override
    @Transactional(isolation= Isolation.SERIALIZABLE)
    public Membership sendToLoanAccount(double amount) {
        logger.info("getting membership account...");
        Membership membership =getMembership();
        if(membership.getAmount()<amount){
            throw  new InvalidValuesException("invalid amount...");
        }
        String userName =myUserDetailsService.currentUser();
        logger.info("updating sacco loanAccount...");
        LoanAccount loanAccount =new LoanAccount();
        loanAccount.setAmount(amount);
        loanAccountService.updateLoanAccount(loanAccount);

        logger.info("Transactions loanT and MembershipT...");
        LoanTDto loanTDto=new LoanTDto();
        loanTDto.setTransactionType(TransactionType.INTERNAL);
        loanTDto.setAccount(loanAccount.getName());
        loanTDto.setCreationDateTime(new Date());
        loanTDto.setWallet(membership.getName());
        loanTDto.setNarrative("transfer membership to loanAccount");
        loanTDto.setStatus(TransactionStatus.PENDING);
        loanTDto.setDate(new Date());
        loanTDto.setAmount(amount);
        loanTDto.setUserName(userName);
        loanTService.saveLoanTransaction(loanTDto);

        logger.info("saving MembershipT...");
        MembershipTDto membershipTDto =new MembershipTDto();
        membershipTDto.setTransactionType(TransactionType.INTERNAL);
        membershipTDto.setWallet(loanAccount.getName());
        membershipTDto.setUserName(userName);
        membershipTDto.setStatus(TransactionStatus.PENDING);
        membershipTDto.setNarrative("transfer membership to loanAccount");
        membershipTDto.setDate(new Date());
        membershipTDto.setCreationDateTime(new Date());
        membershipTDto.setAmount(amount*-1);
        membershipTDto.setAccount(membership.getName());
        membershipTService.saveMembershipTransaction(membershipTDto);
        logger.info("updating membership account...");
        membership.setAmount(membership.getAmount()-amount);
        return membershipDao.save(membership);
    }
    
}
