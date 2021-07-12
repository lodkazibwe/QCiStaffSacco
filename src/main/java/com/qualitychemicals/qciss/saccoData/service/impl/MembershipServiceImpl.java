package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.MembershipDao;
import com.qualitychemicals.qciss.saccoData.model.Membership;
import com.qualitychemicals.qciss.saccoData.service.MembershipService;
import com.qualitychemicals.qciss.saccoData.service.SaccoAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    SaccoAccountService saccoAccountService;
    @Autowired
    MembershipDao membershipDao;

    @Override
    public Membership addMembership(Membership membership) {
        boolean bool =saccoAccountService.existsByName("MEMBERSHIP");
        if(bool){
            return null;
        }
        membership.setName("MEMBERSHIP");
        return membershipDao.save(membership);
    }

    @Override
    public Membership updateMembership(double amount) {
        Membership membership =getMembership();
        membership.setAmount(amount+membership.getAmount());
        return membershipDao.save(membership);
    }

    @Override
    public Membership getMembership() {

        return membershipDao.findByName("MEMBERSHIP");
    }
}
