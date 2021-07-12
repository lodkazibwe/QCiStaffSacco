package com.qualitychemicals.qciss.account.service;

import com.qualitychemicals.qciss.account.dto.SharesAccountDto;
import com.qualitychemicals.qciss.account.model.SharesAccount;
import com.qualitychemicals.qciss.account.model.UserAccount;

import java.util.List;

public interface SharesAccountService {
    SharesAccount addSharesAccount(SharesAccountDto sharesAccountDto);
    SharesAccount transact(UserAccount userAccount);
    SharesAccount getSharesAccount(String accountRef);
    SharesAccount getMyAccount();

    List<SharesAccount> getAll();
    List<SharesAccount> getBySharesLess(double shares);
    List<SharesAccount> getBySharesGreater(double shares);
    SharesAccount updateSharesAccount(SharesAccountDto sharesAccountDto);
}
