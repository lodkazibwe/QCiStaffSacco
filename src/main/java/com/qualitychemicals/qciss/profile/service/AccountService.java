package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.model.Account;

import java.util.List;

public interface AccountService {
    Account createAccount(AccountDto accountDto);
    Account updateAccount(AccountDto accountDto, int id);
    Account getSummary(int id);
    List<Account> getAll();
    void updateSaving(double amount, String userName);
    void updateMembership(double amount, String userName);
    void updateShares(double shares, String userName);

    void deleteSummary(int id);

    Account myAccount(String userName);
}
