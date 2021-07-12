package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.DayAccountDao;
import com.qualitychemicals.qciss.saccoData.model.DayAccount;
import com.qualitychemicals.qciss.saccoData.service.DayAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DayAccountServiceImpl implements DayAccountService {
    @Autowired DayAccountDao dayAccountDao;
    @Override
    public DayAccount addDayAccount(DayAccount dayAccount) {
        boolean bool =existsByDateName(dayAccount.getDate(), dayAccount.getName());
        if(bool){
            return null;
        }
        return dayAccountDao.save(dayAccount);
    }

    @Override
    public DayAccount updateDayAccount(DayAccount dayAccount) {
        return null;
    }

    @Override
    public List<DayAccount> getDayAccount(Date date) {
        return dayAccountDao.findByDate(date);
    }

    @Override
    public List<DayAccount> getDayAccount(Date dateFrom, Date dateTo) {
        return dayAccountDao.findByDateGreaterThanEqualAndDateLessThanEqual(dateFrom, dateTo);
    }

    private boolean existsByDateName(Date date, String name){
                   return dayAccountDao.existsByDateAndName(date, name);
               }
}
