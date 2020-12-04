package com.qualitychemicals.qciss.transaction.service;


import com.qualitychemicals.qciss.transaction.dto.ShareTDto;
import com.qualitychemicals.qciss.transaction.dto.TransactionDto;

public interface ShareTService {
    ShareTDto mobileShares(double amount);
}
