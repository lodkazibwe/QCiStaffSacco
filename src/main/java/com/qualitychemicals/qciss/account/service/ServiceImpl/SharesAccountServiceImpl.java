package com.qualitychemicals.qciss.account.service.ServiceImpl;

import com.qualitychemicals.qciss.account.converter.SharesAccountConverter;
import com.qualitychemicals.qciss.account.dao.SharesAccountDao;
import com.qualitychemicals.qciss.account.dto.SharesAccountDto;
import com.qualitychemicals.qciss.account.model.SharesAccount;
import com.qualitychemicals.qciss.account.model.UserAccount;
import com.qualitychemicals.qciss.account.service.SharesAccountService;
import com.qualitychemicals.qciss.account.service.UserAccountService;
import com.qualitychemicals.qciss.account.service.WalletService;
import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.saccoData.model.Share;
import com.qualitychemicals.qciss.saccoData.service.ShareService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SharesAccountServiceImpl implements SharesAccountService {
    @Autowired UserAccountService userAccountService;
    @Autowired SharesAccountConverter sharesAccountConverter;
    @Autowired SharesAccountDao sharesAccountDao;
    @Autowired ShareService shareService;
    @Autowired MyUserDetailsService myUserDetailsService;
    @Autowired
    WalletService walletService;

    @Transactional
    @Override
    public SharesAccount getMyAccount() {
        walletService.refresh();
        return getSharesAccount("SHA"+myUserDetailsService.currentUser());
    }

    @Override
    public SharesAccount addSharesAccount(SharesAccountDto sharesAccountDto) {
        boolean bool =userAccountService.existsByRef(sharesAccountDto.getAccountRef());
        if(bool){
            throw new InvalidValuesException("account already Exists");
        }
        return sharesAccountDao.save(sharesAccountConverter.dtoToEntity(sharesAccountDto));
    }

    @Override
    public SharesAccount transact(UserAccount userAccount) {
        SharesAccount sharesAccount=getSharesAccount(userAccount.getAccountRef());
        if(sharesAccount!=null){
            Share share =shareService.getShareInfo();
        sharesAccount.setLastTransaction(userAccount.getLastTransaction());
        sharesAccount.setAmount(userAccount.getAmount()+sharesAccount.getAmount());
        sharesAccount.setShares((userAccount.getAmount()/share.getShareValue())+sharesAccount.getShares());
            return sharesAccountDao.save(sharesAccount);
        }
        throw new ResourceNotFoundException("No such account: "+ userAccount.getAccountRef());

    }

    @Override
    public SharesAccount getSharesAccount(String accountRef) {
        SharesAccount sharesAccount =sharesAccountDao.findByAccountRef(accountRef);
        if (sharesAccount == null) {
            throw new ResourceNotFoundException("No such account: ");
        }

        return sharesAccount;
    }

    @Override
    public List<SharesAccount> getAll() {
        return sharesAccountDao.findAll();
    }

    @Override
    public List<SharesAccount> getBySharesLess(double shares) {
        return sharesAccountDao.findBySharesLessThan(shares);
    }

    @Override
    public List<SharesAccount> getBySharesGreater(double shares) {
        return sharesAccountDao.findBySharesGreaterThan(shares);
    }

    @Override
    public SharesAccount updateSharesAccount(SharesAccountDto sharesAccountDto) {
        return null;
    }
}
