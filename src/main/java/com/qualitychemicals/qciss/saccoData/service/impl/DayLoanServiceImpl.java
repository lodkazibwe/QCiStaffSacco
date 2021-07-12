package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.DayLoanDao;
import com.qualitychemicals.qciss.saccoData.model.DayLoan;
import com.qualitychemicals.qciss.saccoData.service.DayLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DayLoanServiceImpl implements DayLoanService {
    @Autowired DayLoanDao dayLoanDao;
    @Override
    public DayLoan addDayLoan(DayLoan dayLoan) {
        boolean bool =existsByDate(dayLoan.getDate());
        if(bool){
            return null;
        }

        return dayLoanDao.save(dayLoan);
    }

    @Override
    public DayLoan updateDayLoan(DayLoan dayLoan) {
    return null;
    }

    @Override
    public DayLoan getDayLoan(Date date) {
        return dayLoanDao.findByDate(date);
    }

    @Override
    public List<DayLoan> getDayLoans(Date dateFrom, Date dateTo) {
        return dayLoanDao.findByDateGreaterThanEqualAndDateLessThanEqual(dateFrom, dateTo);
    }

    private boolean existsByDate(Date date){
        return dayLoanDao.existsByDate(date);
    }
}
