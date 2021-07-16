package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.ExternalAccount;

import java.util.List;

public interface ExternalAccountService {


   ExternalAccount addAccount(ExternalAccount e);
       ExternalAccount updateAccount(ExternalAccount e);
       ExternalAccount getSaccoAccount(String name);
       List<ExternalAccount> getAllAccounts();
       boolean existsByName(String name);
}
