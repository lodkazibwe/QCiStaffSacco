package com.qualitychemicals.qciss.account.service;

import com.qualitychemicals.qciss.account.dto.MembershipAccountDto;
import com.qualitychemicals.qciss.account.model.MembershipAccount;
import com.qualitychemicals.qciss.account.model.UserAccount;

import java.util.List;

public interface MembershipAccountService {
    MembershipAccount addMembershipAccount(MembershipAccountDto m);
     MembershipAccount transact(UserAccount userAccount);
            MembershipAccount getMembershipAccount(String accountRef);
    MembershipAccount getMyAccount();

            List<MembershipAccount> getAll();
            List<MembershipAccount> getByAmountLess(double amount);
            List<MembershipAccount> getByAmountGreater(double amount);
            MembershipAccount updateMembershipAccount(MembershipAccountDto s);
}
