package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.LoanAccount;

public interface LoanAccountService {

    LoanAccount addLoanAccount(LoanAccount loanAccount);

    LoanAccount updateLoanAccount(LoanAccount loanAccount);
    LoanAccount resetLoanAccount(LoanAccount loanAccount);
    LoanAccount getLoanAccount();//String name
    int getCount();
    LoanAccount loanAccountToMembership(double amount);
    LoanAccount loanAccountToSaving(double amount);
    LoanAccount loanAccountToShare(double amount);
}
