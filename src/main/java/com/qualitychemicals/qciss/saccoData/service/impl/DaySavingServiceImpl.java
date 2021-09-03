package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.DaySavingDao;
import com.qualitychemicals.qciss.saccoData.model.DaySaving;
import com.qualitychemicals.qciss.saccoData.model.Saving;
import com.qualitychemicals.qciss.saccoData.service.DaySavingService;
import com.qualitychemicals.qciss.saccoData.service.SavingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DaySavingServiceImpl implements DaySavingService {
    @Autowired DaySavingDao daySavingDao;
    @Autowired SavingService savingService;

     private final Logger logger = LoggerFactory.getLogger(DaySavingServiceImpl.class);

    @Transactional
    @Scheduled(cron="0 50 7 * * *",zone = "EAT")
    public void addDaySaving() {
        logger.info("getting sacco saving account...");
        Saving saving= savingService.getSavingInfo();
        DaySaving daySaving =new DaySaving();
        daySaving.setDaySaving(saving.getDaySaving());
        daySaving.setBalCf(saving.getAmount());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date= cal.getTime();
        daySaving.setDate(date);
        daySaving.setDate(date);
        daySaving.setName(saving.getName());
        logger.info("resetting sacco saving account...");
        savingService.resetSaving(saving.getDaySaving());
        logger.info("saving sacco saving history...");
        daySavingDao.save(daySaving);
    }


    @Override
    public DaySaving getDaySaving(Date date) {
        return daySavingDao.findByDate(date);
    }

    @Override
    public List<DaySaving> getDaySavings(Date dateFrom, Date dateTo) {
        return daySavingDao.findByDateGreaterThanEqualAndDateLessThanEqual(dateFrom, dateTo);
    }

     /*private boolean existsByDate(Date date){
               return daySavingDao.existsByDate(date);
          }*/
}
