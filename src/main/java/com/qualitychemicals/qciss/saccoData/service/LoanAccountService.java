package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.LoanAccount;


public interface LoanAccountService {
    LoanAccount addLoanAccount(LoanAccount loanAccount);

    LoanAccount updateLoanAccount(LoanAccount loanAccount);

    LoanAccount getLoanAccount();//String name
    int getCount();
}
