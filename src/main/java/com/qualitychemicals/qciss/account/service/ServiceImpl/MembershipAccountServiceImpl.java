package com.qualitychemicals.qciss.account.service.ServiceImpl;

import com.qualitychemicals.qciss.account.converter.MembershipAccountConverter;
import com.qualitychemicals.qciss.account.dao.MembershipAccountDao;
import com.qualitychemicals.qciss.account.dto.MembershipAccountDto;
import com.qualitychemicals.qciss.account.model.MembershipAccount;
import com.qualitychemicals.qciss.account.model.UserAccount;
import com.qualitychemicals.qciss.account.service.MembershipAccountService;
import com.qualitychemicals.qciss.account.service.UserAccountService;
import com.qualitychemicals.qciss.account.service.WalletService;
import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.saccoData.model.Membership;
import com.qualitychemicals.qciss.saccoData.service.MembershipService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MembershipAccountServiceImpl implements MembershipAccountService {
    @Autowired
    UserAccountService userAccountService;
    @Autowired MembershipAccountDao membershipAccountDao;
    @Autowired MembershipAccountConverter membershipAccountConverter;
    @Autowired MembershipService membershipService;
    @Autowired
    WalletService walletService;
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    public MembershipAccount addMembershipAccount(MembershipAccountDto membershipAccountDto) {
        boolean bool =userAccountService.existsByRef(membershipAccountDto.getAccountRef());
        if(bool){
            throw new InvalidValuesException("account already Exists");
        }
        return membershipAccountDao.save(membershipAccountConverter.dtoToEntity(membershipAccountDto));
    }

    @Transactional
    @Override
    public MembershipAccount getMyAccount() {
        walletService.refresh();
        return getMembershipAccount("MEM"+myUserDetailsService.currentUser());

    }

    @Override
    public MembershipAccount transact(UserAccount userAccount) {
        MembershipAccount membershipAccount =getMembershipAccount(userAccount.getAccountRef());
        if(membershipAccount!=null){
            Membership membership =membershipService.getMembership();
            double value=membership.getMembershipFee();
            double amount= userAccount.getAmount()+membershipAccount.getAmount();
            double diff =value-amount;
            membershipAccount.setLastTransaction(userAccount.getLastTransaction());
            membershipAccount.setAmount(amount);
            if(diff>=0){
                membershipAccount.setBalance(diff);
                membershipAccount.setSurplus(0);
            }else{
                membershipAccount.setBalance(0);
                membershipAccount.setSurplus(amount-value);
            }
            return membershipAccountDao.save(membershipAccount);

        }
        throw new ResourceNotFoundException("No such account: "+ userAccount.getAccountRef());
    }

    @Override
    public MembershipAccount getMembershipAccount(String accountRef) {

        MembershipAccount membershipAccount =membershipAccountDao.findByAccountRef(accountRef);
        if (membershipAccount == null) {
            throw new ResourceNotFoundException("No such account: ");
        }
        return  membershipAccount;

    }

    @Override
    public List<MembershipAccount> getAll() {
        return membershipAccountDao.findAll();
    }

    @Override
    public List<MembershipAccount> getByAmountLess(double amount) {
        return membershipAccountDao.findByAmountLessThan(amount);
    }

    @Override
    public List<MembershipAccount> getByAmountGreater(double amount) {
        return membershipAccountDao.findByAmountGreaterThan(amount);
    }

    @Override
    public MembershipAccount updateMembershipAccount(MembershipAccountDto membershipAccountDto) {
        return null;
    }
}
