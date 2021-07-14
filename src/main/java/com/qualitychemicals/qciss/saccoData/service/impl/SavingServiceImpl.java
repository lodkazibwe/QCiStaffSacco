package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.SavingDao;
import com.qualitychemicals.qciss.saccoData.model.Saving;
import com.qualitychemicals.qciss.saccoData.service.SaccoAccountService;
import com.qualitychemicals.qciss.saccoData.service.SavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class SavingServiceImpl implements SavingService {
    @Autowired SavingDao savingDao;
    @Autowired
    SaccoAccountService saccoAccountService;

    @Override
    public Saving addSaving(Saving saving) {
        boolean bool =saccoAccountService.existsByName("SAVING");
        if(bool){
            throw new ResourceAccessException("account already exists");
        }
        saving.setName("SAVING");
        return savingDao.save(saving);
    }

    @Override
    public Saving updateSaving(double amount) {
        Saving saving =getSavingInfo();
        saving.setAmount(amount+saving.getAmount());
        return savingDao.save(saving);
    }

    @Override
    public Saving getSavingInfo() {
        Saving saving =savingDao.findByName("SAVING");
        if (saving == null) {
            throw new ResourceAccessException("admin sav_account not found");
        }
        return saving;
    }
}
