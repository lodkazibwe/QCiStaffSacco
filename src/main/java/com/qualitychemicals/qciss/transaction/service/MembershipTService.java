package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.MembershipTDto;
import com.qualitychemicals.qciss.transaction.dto.MembershipTransactionsDto;

public interface MembershipTService {
    MembershipTDto payMembership(double amount);
    MembershipTDto payMembership(MembershipTDto membershipTDto);
    void initialMembership(double amount, String userName);

    MembershipTransactionsDto membershipTransactions();
}
