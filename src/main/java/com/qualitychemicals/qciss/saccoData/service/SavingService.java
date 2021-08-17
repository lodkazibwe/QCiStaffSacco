package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.Saving;


public interface SavingService {
    Saving addSaving(Saving saving);
    Saving updateSaving(double amount);
    Saving resetSaving(double amount);
    Saving getSavingInfo();
    Saving sendToLoanAccount(double amount);

}
