package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.Membership;

import java.util.List;

public interface MembershipService {
    Membership addMembership(Membership membership);
    Membership updateMembership(double amount);
    Membership getMembership();
}
