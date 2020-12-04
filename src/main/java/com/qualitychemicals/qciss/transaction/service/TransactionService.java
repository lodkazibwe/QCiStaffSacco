package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.*;


public interface TransactionService {
    MobilePayment transactMobile(MobilePayment mobilePayment);
    TransactionDto receiveMobileMoney(double amount);
    LoanTransactionsDto loanTransactions(int loanId);
    UserTransactionsDto userTransactions(String userName);
    UserTransactionsDto myTransactions();
    LoanTransactionsDto myLoanTransactions(int loanId);
}
