package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.DateTransactions;
import com.qualitychemicals.qciss.transaction.dto.MembershipTDto;
import com.qualitychemicals.qciss.transaction.dto.MembershipTransactionsDto;

import java.util.Date;

public interface MembershipTService {
    MembershipTDto payMembership(double amount);
    MembershipTDto payMembership(MembershipTDto membershipTDto);
    void initialMembership(double amount, String userName);

    MembershipTransactionsDto membershipTransactions();

    double totalMembership(Date date);
    double totalMembership(Date dateFrom, Date dateTo);

    DateTransactions dateMembership(Date dateFrom, Date dateTo);
}
