package com.qualitychemicals.qciss.transaction.service;


import com.qualitychemicals.qciss.transaction.dto.ShareTDto;
import com.qualitychemicals.qciss.transaction.dto.SharesTransactionsDto;

public interface ShareTService {
    ShareTDto mobileShares(double amount);
    ShareTDto systemShares(ShareTDto shareTDto);
    void initialShares(double qtty, String userName);

    SharesTransactionsDto shareTransactions();
}
