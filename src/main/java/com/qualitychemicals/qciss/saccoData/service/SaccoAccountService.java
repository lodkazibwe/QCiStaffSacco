package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.SaccoAccount;

import java.util.List;

public interface SaccoAccountService {
    SaccoAccount addAccount(SaccoAccount saccoAccount);
    SaccoAccount updateAccount(SaccoAccount saccoAccount);
    SaccoAccount getSaccoAccount(String name);
    List<SaccoAccount> getAllAccounts();
    boolean existsByName(String name);

}
