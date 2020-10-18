package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.transaction.dto.TransactionDto;
import com.qualitychemicals.qciss.transaction.model.LoanT;
import com.qualitychemicals.qciss.transaction.model.Transaction;

public interface TransactionService {
    Transaction verify(TransactionDto transactionDto);

}
