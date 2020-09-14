package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.model.Account;

import java.util.List;

public interface AccountService {
    Account addAccount(Account account);
    Account getAccount(int id);
    List<Account> getByProfile(int id);
    List<Account> getByCategory(String category);
    List<Account> getAll();
    Account updateAccount(Account account);
    void deleteAccount(int id);

}
