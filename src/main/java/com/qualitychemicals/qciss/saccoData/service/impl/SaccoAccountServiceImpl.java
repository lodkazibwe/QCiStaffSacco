package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.SaccoAccountDao;
import com.qualitychemicals.qciss.saccoData.model.SaccoAccount;
import com.qualitychemicals.qciss.saccoData.service.SaccoAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class SaccoAccountServiceImpl implements SaccoAccountService {
    @Autowired SaccoAccountDao saccoAccountDao;
    @Override
    public SaccoAccount addAccount(SaccoAccount saccoAccount) {
        boolean bool =existsByName(saccoAccount.getName());
        if(bool){
            throw new ResourceAccessException("account already exists");
        }

        return saccoAccountDao.save(saccoAccount);
    }

    @Override
    public SaccoAccount updateAccount(SaccoAccount update) {
        SaccoAccount saccoAccount =getSaccoAccount(update.getName());
        saccoAccount.setAmount(update.getAmount()+saccoAccount.getAmount());
        return saccoAccountDao.save(saccoAccount);
    }

    @Override
    public SaccoAccount getSaccoAccount(String name) {
        SaccoAccount saccoAccount =saccoAccountDao.findByName(name);
        if (saccoAccount == null) {
            throw new ResourceAccessException("admin account not found");
        }
        return saccoAccount;

    }

    @Override
    public List<SaccoAccount> getAllAccounts() {
       return saccoAccountDao.findAll();
    }

    @Override
    public boolean existsByName(String name){
        return saccoAccountDao.existsByName(name);
    }
}
