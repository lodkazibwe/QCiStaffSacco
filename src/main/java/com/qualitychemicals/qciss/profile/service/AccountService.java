package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> addAccount(AccountDto accountDTO, int id);
    Account getAccount(int id);
    List<Account> getByCategory(String category);
    List<Account> getAll();
    Account updateAccount(AccountDto accountDTO, int id);
    void deleteAccount(int id);

}
