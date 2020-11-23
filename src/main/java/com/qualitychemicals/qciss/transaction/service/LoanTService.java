package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.LoanPayDto;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.model.LoanT;
import com.qualitychemicals.qciss.transaction.model.TransactionType;

public interface LoanTService {
    LoanT release(int loanId, TransactionType transactionType);
    LoanT repay(LoanTDto loanTDto);
    LoanT payPenalty(LoanTDto loanTDto);
    LoanT repayMobile(LoanPayDto loanPayDto);
}
