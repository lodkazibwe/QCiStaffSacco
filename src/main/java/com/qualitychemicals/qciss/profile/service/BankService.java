package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.dto.BankDto;
import com.qualitychemicals.qciss.profile.model.Bank;

public interface BankService {

    Bank addBank(BankDto bankDto);
    Bank updateBank(BankDto bankDto);
    Bank myBank();

}
