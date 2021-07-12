package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.Saving;

import java.util.List;

public interface SavingService {
    Saving addSaving(Saving saving);
    Saving updateSaving(double amount);
    Saving getSavingInfo();

}
