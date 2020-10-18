package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.MembershipTDto;
import com.qualitychemicals.qciss.transaction.model.MembershipT;

public interface MembershipTService {
    MembershipT payMembership(MembershipTDto membershipTDto);
}
