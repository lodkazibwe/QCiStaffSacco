package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.model.Account;

import java.util.List;

public interface AccountService {
    Account updateAccount(AccountDto accountDto, int id);
    Account getSummary(int id);
    List<Account> getAll();
    void deleteSummary(int id);
}
