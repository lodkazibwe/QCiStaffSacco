package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.ShareDao;
import com.qualitychemicals.qciss.saccoData.model.Share;
import com.qualitychemicals.qciss.saccoData.service.SaccoAccountService;
import com.qualitychemicals.qciss.saccoData.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceImpl implements ShareService {
    @Autowired ShareDao shareDao;
    @Autowired
    SaccoAccountService saccoAccountService;

    @Override
    public Share addShare(Share share) {
        boolean bool =saccoAccountService.existsByName("SHARES");
        if(bool){
            return null;
        }
        return shareDao.save(share);
    }

    @Override
    public Share updateShare(Share update) {
        Share share =getShareInfo();
        share.setSharesAvailable(update.getSharesAvailable()+share.getSharesAvailable());
        share.setSharesSold(update.getSharesSold()+share.getSharesSold());
        share.setAmount(update.getAmount()+share.getAmount());
        return shareDao.save(share);
    }

    @Override
    public Share getShareInfo() {
        return shareDao.findByName("SHARES");
    }
}
