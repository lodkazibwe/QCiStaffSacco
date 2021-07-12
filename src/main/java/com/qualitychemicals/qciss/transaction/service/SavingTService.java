package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.DateTransactions;
import com.qualitychemicals.qciss.transaction.dto.SavingTDto;
import com.qualitychemicals.qciss.transaction.dto.SavingsTransactionsDto;

import java.util.Date;

public interface SavingTService {
    SavingTDto mobileSaving(double amount);
    SavingTDto systemSaving(SavingTDto savingTDto);
    //void initialSaving(double amount, String userName);

    double totalSaving(Date date);
    double totalSaving(Date dateFrom, Date dateTo);

    DateTransactions dateSaving(Date dateFrom, Date dateTo);

    SavingsTransactionsDto savingTransactions();
}
