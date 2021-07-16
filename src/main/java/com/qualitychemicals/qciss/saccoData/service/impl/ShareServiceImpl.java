package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.ShareDao;
import com.qualitychemicals.qciss.saccoData.model.Share;
import com.qualitychemicals.qciss.saccoData.service.SaccoAccountService;
import com.qualitychemicals.qciss.saccoData.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

@Service
public class ShareServiceImpl implements ShareService {
    @Autowired ShareDao shareDao;
    @Autowired
    SaccoAccountService saccoAccountService;

    @Override
    public Share addShare(Share share) {
        boolean bool =saccoAccountService.existsByName("SHARES");
        if(bool){
            throw new ResourceAccessException("account already exists");
        }
        return shareDao.save(share);
    }

    @Override
    public Share updateShare(Share update) {
        Share share =getShareInfo();
        share.setSharesAvailable(update.getSharesAvailable()+share.getSharesAvailable());
        share.setSharesSold(update.getSharesSold()+share.getSharesSold());
        share.setAmount(update.getAmount()+share.getAmount());
        share.setDayShares(update.getDayShares()+share.getDayShares());
        share.setDayShareAmount(update.getDayShareAmount()+share.getDayShareAmount());
        return shareDao.save(share);
    }

    @Override
    public Share getShareInfo() {
        Share share =shareDao.findByName("SHARES");
        if (share == null) {
            throw new ResourceAccessException("admin sha_account not found");
        }
        return share;
    }
}
