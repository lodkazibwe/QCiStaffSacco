package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.DateTransactions;
import com.qualitychemicals.qciss.transaction.dto.LoanPayDto;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;

import java.util.Date;

public interface LoanTService {
    LoanTDto release(LoanPayDto loanPayDto);
    LoanTDto repay(LoanTDto loanTDto);
    LoanTDto payPenalty(LoanTDto loanTDto);
    LoanTDto repayMobile(LoanPayDto loanPayDto);

    double totalRepay(Date date);
    double totalRepay(Date dateFrom, Date dateTo);

    DateTransactions dateRepay(Date dateFrom, Date dateTo);
}
