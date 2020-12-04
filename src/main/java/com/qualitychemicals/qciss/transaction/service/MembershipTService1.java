package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.MembershipTDto;

public interface MembershipTService1 {
    MembershipTDto payMembership(double amount);
    MembershipTDto payMembership(MembershipTDto membershipTDto);
}
