package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.DayMembershipDao;
import com.qualitychemicals.qciss.saccoData.model.DayMembership;
import com.qualitychemicals.qciss.saccoData.model.Membership;
import com.qualitychemicals.qciss.saccoData.service.DayMembershipService;
import com.qualitychemicals.qciss.saccoData.service.MembershipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DayMembershipServiceImpl implements DayMembershipService {
    @Autowired DayMembershipDao dayMembershipDao;
    @Autowired
    MembershipService membershipService;

    private final Logger logger = LoggerFactory.getLogger(DayMembershipServiceImpl.class);

    @Transactional
    @Scheduled(cron = "30 59 23 * * *",zone = "EAT")
    public void addDayMembership() {
        logger.info("getting membership account...");
        Membership membership =membershipService.getMembership();
        DayMembership dayMembership =new DayMembership();
        dayMembership.setDayMembership(membership.getDayMembership());
        dayMembership.setBalCf(membership.getAmount());
        dayMembership.setDate(new Date());
        dayMembership.setName(membership.getName());
        logger.info("resetting membership account...");
        membershipService.resetMembership(membership.getDayMembership());
        logger.info("saving membership history...");
        dayMembershipDao.save(dayMembership);
    }

    @Override
    public DayMembership getDayMembership(Date date) {
        return dayMembershipDao.findByDate(date);
    }

    @Override
    public List<DayMembership> getDayMemberships(Date dateFrom, Date dateTo) {
        return dayMembershipDao.findByDateGreaterThanEqualAndDateLessThanEqual(dateFrom, dateTo);
    }

    /*private boolean existsByDate(Date date){
           return dayMembershipDao.existsByDate(date);
       }*/
}
