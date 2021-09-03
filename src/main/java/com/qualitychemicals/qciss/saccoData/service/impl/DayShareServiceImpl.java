package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.DayShareDao;
import com.qualitychemicals.qciss.saccoData.model.DayShare;
import com.qualitychemicals.qciss.saccoData.model.Share;
import com.qualitychemicals.qciss.saccoData.service.DayShareService;
import com.qualitychemicals.qciss.saccoData.service.ShareService;
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
public class DayShareServiceImpl implements DayShareService {
    @Autowired DayShareDao dayShareDao;
    @Autowired ShareService shareService;

    private final Logger logger = LoggerFactory.getLogger(DayShareServiceImpl.class);

    @Transactional
    @Scheduled(cron="0 05 0 * * *",zone = "EAT")
    public void addDayShare() {
        logger.info("getting sacco share account...");
        Share share =shareService.getShareInfo();
        DayShare dayShare =new DayShare();
        dayShare.setDayShareAmount(share.getDayShareAmount());
        dayShare.setDayShares(share.getDayShares());
        dayShare.setBalCf(share.getAmount());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date= cal.getTime();
        dayShare.setDate(date);
        dayShare.setDate(date);
        dayShare.setName(share.getName());
        dayShare.setSharesSold(share.getSharesSold());
        dayShare.setSharesAvailable(share.getSharesAvailable());
        dayShare.setShareValue(share.getShareValue());
        logger.info("resetting sacco share account...");
        shareService.resetShare(share);
        logger.info("saving sacco share history...");
        dayShareDao.save(dayShare);

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
