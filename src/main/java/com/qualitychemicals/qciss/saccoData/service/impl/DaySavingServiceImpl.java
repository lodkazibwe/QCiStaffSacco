package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.DaySavingDao;
import com.qualitychemicals.qciss.saccoData.model.DaySaving;
import com.qualitychemicals.qciss.saccoData.service.DaySavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DaySavingServiceImpl implements DaySavingService {
    @Autowired DaySavingDao daySavingDao;

    @Override
    public DaySaving addDaySaving(DaySaving daySaving) {
        boolean bool =existsByDate(daySaving.getDate());
        if(bool){
            return null;
        }
        return daySavingDao.save(daySaving);
    }

    @Override
    public DaySaving updateDaySaving(DaySaving daySaving) {
        return null;
    }

    @Override
    public DaySaving getDaySaving(Date date) {
        return daySavingDao.findByDate(date);
    }

    @Override
    public List<DaySaving> getDaySavings(Date dateFrom, Date dateTo) {
        return daySavingDao.findByDateGreaterThanEqualAndDateLessThanEqual(dateFrom, dateTo);
    }

     private boolean existsByDate(Date date){
               return daySavingDao.existsByDate(date);
          }
}
