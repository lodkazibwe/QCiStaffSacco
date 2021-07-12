package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.model.Share;

import java.util.List;

public interface ShareService {
    Share addShare(Share share);
    Share updateShare(Share share);
    Share getShareInfo();
}
