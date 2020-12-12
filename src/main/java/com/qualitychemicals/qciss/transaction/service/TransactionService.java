package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.*;


public interface TransactionService {
    MobilePayment transactMobile(MobilePayment mobilePayment);
    TransactionDto receiveMobileMoney(double amount, TransactionCat category);
    LoanTransactionsDto loanTransactions(int loanId);
    UserTransactionsDto userTransactions(String userName);
    UserTransactionsDto myTransactions();
    LoanTransactionsDto myLoanTransactions(int loanId);
    LoanTransactionsDto loanTransactions();
    AllTransactions allTransactions();
    SavingsTransactionsDto savingTransactions();
    MembershipTransactionsDto membershipTransactions();
    SharesTransactionsDto shareTransactions();
    AllTransactions allByType(TransactionType transactionType);
}
