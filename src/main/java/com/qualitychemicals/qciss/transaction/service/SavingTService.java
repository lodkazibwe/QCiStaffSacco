package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.DateTransactions;
import com.qualitychemicals.qciss.transaction.dto.MembershipTransactionsDto;
import com.qualitychemicals.qciss.transaction.dto.SavingTDto;
import com.qualitychemicals.qciss.transaction.dto.SavingsTransactionsDto;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public interface SavingTService {
    SavingTDto mobileSaving(double amount);
    SavingsTransactionsDto myAll();
    SavingsTransactionsDto myRecent();
    SavingsTransactionsDto allByWallet(String wallet);
    SavingsTransactionsDto last5ByWallet(String wallet);
    SavingTDto saveSavingTransaction(SavingTDto savingTDto);


    SavingTDto systemSaving(SavingTDto savingTDto);
    //void initialSaving(double amount, String userName);

    double totalSaving(Date date);
    double totalSaving(Date dateFrom, Date dateTo);

    DateTransactions dateSaving(Date dateFrom, Date dateTo);

    SavingsTransactionsDto savingTransactions();
}
