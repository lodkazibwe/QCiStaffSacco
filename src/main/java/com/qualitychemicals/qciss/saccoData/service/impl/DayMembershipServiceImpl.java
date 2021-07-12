package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.DayMembershipDao;
import com.qualitychemicals.qciss.saccoData.model.DayMembership;
import com.qualitychemicals.qciss.saccoData.service.DayMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DayMembershipServiceImpl implements DayMembershipService {
    @Autowired DayMembershipDao dayMembershipDao;
    @Override
    public DayMembership addDayMembership(DayMembership dayMembership) {
        boolean bool =existsByDate(dayMembership.getDate());
        if(bool){
            return null;
        }
        return dayMembershipDao.save(dayMembership);
    }

    @Override
    public DayMembership updateDayMembership(DayMembership dayMembership) {
        return null;
    }

    @Override
    public DayMembership getDayMembership(Date date) {
        return dayMembershipDao.findByDate(date);
    }

    @Override
    public List<DayMembership> getDayMemberships(Date dateFrom, Date dateTo) {
        return dayMembershipDao.findByDateGreaterThanEqualAndDateLessThanEqual(dateFrom, dateTo);
    }

    private boolean existsByDate(Date date){
           return dayMembershipDao.existsByDate(date);
       }
}
