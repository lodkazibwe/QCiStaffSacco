package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.*;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;

public interface SavingTService {
    SavingTDto mobileSaving(double amount);
    SavingTDto withdrawRequest(double amount);
    SavingsTransactionsDto myAll();
    SavingsTransactionsDto myRecent();
    SavingsTransactionsDto allByWallet(String wallet);
    SavingsTransactionsDto last5ByWallet(String wallet);
    SavingTDto saveSavingTransaction(SavingTDto savingTDto);
    List<CumulativeSavingT> myAllCumulative();



    SavingTDto systemSaving(SavingTDto savingTDto);
    //void initialSaving(double amount, String userName);

    double totalSaving(Date date);
    double totalSaving(Date dateFrom, Date dateTo);

    DateTransactions dateSaving(Date dateFrom, Date dateTo);

    SavingsTransactionsDto savingTransactions();
}
