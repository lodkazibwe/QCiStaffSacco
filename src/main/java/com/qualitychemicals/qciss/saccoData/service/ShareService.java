package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.Share;

public interface ShareService {
    Share addShare(Share share);
    Share updateShare(Share share);
    Share resetShare(Share share);
    Share getShareInfo();
    Share sendToLoanAccount(double amount);
}
