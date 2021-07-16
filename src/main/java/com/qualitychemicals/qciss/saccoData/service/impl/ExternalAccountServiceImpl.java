package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.ExternalAccountDao;
import com.qualitychemicals.qciss.saccoData.model.ExternalAccount;
import com.qualitychemicals.qciss.saccoData.service.ExternalAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class ExternalAccountServiceImpl implements ExternalAccountService {
    @Autowired ExternalAccountDao externalAccountDao;
    @Override
    public ExternalAccount addAccount(ExternalAccount externalAccount) {
        boolean bool =existsByName(externalAccount.getName());
        if(bool){
            throw new ResourceAccessException("account already exists");
        }

        return externalAccountDao.save(externalAccount);
    }

    @Override
    public ExternalAccount updateAccount(ExternalAccount update) {
        ExternalAccount externalAccount =getSaccoAccount(update.getName());
        externalAccount.setAmount(update.getAmount()+externalAccount.getAmount());
        return externalAccountDao.save(externalAccount);
    }

    @Override
    public ExternalAccount getSaccoAccount(String name) {
        ExternalAccount externalAccount =externalAccountDao.findByName(name);
        if (externalAccount == null) {
            throw new ResourceAccessException("ext account not found");
        }
        return externalAccount;
    }

    @Override
    public List<ExternalAccount> getAllAccounts() {
        return externalAccountDao.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return externalAccountDao.existsByName(name);
    }
}
