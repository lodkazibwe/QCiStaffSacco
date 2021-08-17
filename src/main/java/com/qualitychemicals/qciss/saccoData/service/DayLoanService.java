package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.DayLoan;

import java.util.Date;
import java.util.List;

public interface DayLoanService {
    DayLoan getDayLoan(Date date);
    List<DayLoan> getDayLoans(Date dateFrom, Date dateTo);
}
