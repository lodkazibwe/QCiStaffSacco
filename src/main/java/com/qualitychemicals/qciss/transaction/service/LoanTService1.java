package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.LoanPayDto;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;

public interface LoanTService1 {
    LoanTDto release(LoanPayDto loanPayDto);
    LoanTDto repay(LoanTDto loanTDto);
    LoanTDto payPenalty(LoanTDto loanTDto);
    LoanTDto repayMobile(LoanPayDto loanPayDto);
}
