package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.DayShareDao;
import com.qualitychemicals.qciss.saccoData.model.DayShare;
import com.qualitychemicals.qciss.saccoData.service.DayShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DayShareServiceImpl implements DayShareService {
    @Autowired DayShareDao dayShareDao;
    @Override
    public DayShare addDayShare(DayShare dayShare) {
        boolean bool =existsByDate(dayShare.getDate());
        if(bool){
            return null;
        }
        return dayShareDao.save(dayShare);
    }

    @Override
    public DayShare updateDayShare(DayShare dayShare) {
        return null;
    }

    @Override
    public DayShare getDayShare(Date date) {
        return dayShareDao.findByDate(date);
    }

    @Override
    public List<DayShare> getDayShares(Date dateFrom, Date dateTo) {
        return dayShareDao.findByDateGreaterThanEqualAndDateLessThanEqual(dateFrom, dateTo);
    }

    private boolean existsByDate(Date date){
               return dayShareDao.existsByDate(date);
           }
}
