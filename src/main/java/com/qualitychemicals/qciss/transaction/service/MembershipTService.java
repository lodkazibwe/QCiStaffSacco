package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.MembershipTDto;
import com.qualitychemicals.qciss.transaction.model.MembershipT;
import com.qualitychemicals.qciss.transaction.model.Transaction;

public interface MembershipTService {
    Transaction payMembership(double amount);
    MembershipT payMembership(MembershipTDto membershipTDto);
}
