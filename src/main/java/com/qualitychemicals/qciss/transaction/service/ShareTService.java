package com.qualitychemicals.qciss.transaction.service;


import com.qualitychemicals.qciss.transaction.dto.DateTransactions;
import com.qualitychemicals.qciss.transaction.dto.ShareTDto;
import com.qualitychemicals.qciss.transaction.dto.SharesTransactionsDto;

import java.util.Date;

public interface ShareTService {
    ShareTDto buyShares(double amount);
    ShareTDto systemShares(ShareTDto shareTDto);
    //void initialShares(double qtty, String userName);
    double totalShares(Date date);
    double totalShares(Date dateFrom, Date dateTo);

    DateTransactions dateShares(Date dateFrom, Date dateTo);

    SharesTransactionsDto shareTransactions();
}
