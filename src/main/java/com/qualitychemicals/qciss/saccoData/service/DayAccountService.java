package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.DayAccount;

import java.util.Date;
import java.util.List;

public interface DayAccountService {
    DayAccount addDayAccount(DayAccount dayAccount);
    DayAccount updateDayAccount(DayAccount dayAccount);
    List<DayAccount> getDayAccount(Date date);
    List<DayAccount> getDayAccount(Date dateFrom, Date dateTo);
}
