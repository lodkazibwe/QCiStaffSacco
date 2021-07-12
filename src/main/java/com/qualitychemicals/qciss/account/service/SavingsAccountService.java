package com.qualitychemicals.qciss.account.service;

import com.qualitychemicals.qciss.account.dto.SavingsAccountDto;
import com.qualitychemicals.qciss.account.model.SavingsAccount;
import com.qualitychemicals.qciss.account.model.UserAccount;

import java.util.List;

public interface SavingsAccountService {
    SavingsAccount addSavingsAccount(SavingsAccountDto savingsAccountDto);
        SavingsAccount transact(UserAccount userAccount);
        SavingsAccount getSavingsAccount(String accountRef);
    SavingsAccount getMyAccount();

        List<SavingsAccount> getAll();
        List<SavingsAccount> getByAmountLess(double amount);
        List<SavingsAccount> getByAmountGreater(double amount);
        SavingsAccount updateSavingsAccount(SavingsAccountDto savingsAccountDto);
}
