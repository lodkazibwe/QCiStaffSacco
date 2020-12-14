package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.SavingTDto;

public interface SavingTService {
    SavingTDto mobileSaving(double amount);
    SavingTDto systemSaving(SavingTDto savingTDto);
}
