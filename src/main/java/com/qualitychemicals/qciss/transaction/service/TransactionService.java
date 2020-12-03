package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.MobilePayment;
import com.qualitychemicals.qciss.transaction.model.LoanT;
import com.qualitychemicals.qciss.transaction.model.Transaction;

import java.util.List;

public interface TransactionService {
    MobilePayment transactMobile(MobilePayment mobilePayment);
    Transaction receiveMobileMoney(double amount);
    List<LoanT> loanTransactions(int loanId);
    List<Transaction> userTransactions(int userId);
}
