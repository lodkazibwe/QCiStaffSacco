package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.DayShare;

import java.util.Date;
import java.util.List;

public interface DayShareService {
    DayShare addDayShare(DayShare dayShare);
    DayShare updateDayShare(DayShare dayShare);
    DayShare getDayShare(Date date);
    List<DayShare> getDayShares(Date dateFrom, Date dateTo);
}
