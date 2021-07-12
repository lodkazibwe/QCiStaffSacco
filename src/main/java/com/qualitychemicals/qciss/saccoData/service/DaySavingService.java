package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.DaySaving;

import java.util.Date;
import java.util.List;

public interface DaySavingService {
    DaySaving addDaySaving(DaySaving daySaving);
    DaySaving updateDaySaving(DaySaving daySaving);
    DaySaving getDaySaving(Date date);
    List<DaySaving> getDaySavings(Date dateFrom, Date dateTo);
}
