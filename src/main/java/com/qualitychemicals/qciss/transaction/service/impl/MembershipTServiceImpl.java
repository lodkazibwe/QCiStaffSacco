package com.qualitychemicals.qciss.transaction.service.impl;

import com.qualitychemicals.qciss.transaction.dao.TransactionDao;
import com.qualitychemicals.qciss.transaction.dto.MembershipTDto;
import com.qualitychemicals.qciss.transaction.model.MembershipT;
import com.qualitychemicals.qciss.transaction.model.Transaction;
import com.qualitychemicals.qciss.transaction.service.MembershipTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipTServiceImpl implements MembershipTService {
    @Autowired TransactionService transactionService;
    TransactionDao transactionDao;
    @Override
    public MembershipT payMembership(MembershipTDto membershipTDto) {
        Transaction transaction=transactionService.verify(membershipTDto);
        MembershipT membershipT=(MembershipT) transaction;
        membershipT.setYear(membershipTDto.getYear());
            //call payment service
        return transactionDao.save(membershipT);    }

}
