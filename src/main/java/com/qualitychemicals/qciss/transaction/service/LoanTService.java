package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.DateTransactions;
import com.qualitychemicals.qciss.transaction.dto.LoanPayDto;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.dto.LoanTransactionsDto;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface LoanTService {
    LoanTDto release(LoanPayDto loanPayDto);
    LoanTDto walletRepay(LoanPayDto loanPayDto);
    LoanTransactionsDto myAll(String loanRef);
    LoanTransactionsDto myAll();
    LoanTransactionsDto myRecent();
    LoanTransactionsDto adminAll(String loanRef);
    LoanTDto saveLoanTransaction(LoanTDto loanTDto);


    //LoanTDto repay(LoanTDto loanTDto);
    LoanTDto payPenalty(LoanTDto loanTDto);
    double totalRepay(Date date);
    double totalRepay(Date dateFrom, Date dateTo);

    DateTransactions dateRepay(Date dateFrom, Date dateTo);
}
