package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.account.model.Wallet;
import com.qualitychemicals.qciss.transaction.dto.DateTransactions;
import com.qualitychemicals.qciss.transaction.dto.MembershipTDto;
import com.qualitychemicals.qciss.transaction.dto.MembershipTransactionsDto;

import java.util.Date;

public interface MembershipTService {
    MembershipTDto payMembership(double amount);
    MembershipTransactionsDto myRecent();
    MembershipTransactionsDto myAll();
    MembershipTransactionsDto allByWallet(String wallet);
    MembershipTransactionsDto last5ByWallet(String wallet);
    MembershipTDto saveMembershipTransaction(MembershipTDto membershipTDto);



    /***delete***/

    MembershipTDto payMembership(MembershipTDto membershipTDto);
    //void initialMembership(double amount, String userName);

    MembershipTransactionsDto membershipTransactions();

    double totalMembership(Date date);
    double totalMembership(Date dateFrom, Date dateTo);

    DateTransactions dateMembership(Date dateFrom, Date dateTo);
}
