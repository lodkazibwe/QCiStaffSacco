package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.Membership;


public interface MembershipService {
    Membership addMembership(Membership membership);
    Membership updateMembership(double amount);
    Membership resetMembership(double amount);
    Membership getMembership();
    Membership sendToLoanAccount(double amount);
}
