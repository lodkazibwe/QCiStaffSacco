package com.qualitychemicals.qciss.account.service.ServiceImpl;

import com.qualitychemicals.qciss.account.converter.SavingsAccountConverter;
import com.qualitychemicals.qciss.account.dao.SavingsAccountDao;
import com.qualitychemicals.qciss.account.dto.SavingsAccountDto;
import com.qualitychemicals.qciss.account.model.SavingsAccount;
import com.qualitychemicals.qciss.account.model.UserAccount;
import com.qualitychemicals.qciss.account.service.SavingsAccountService;
import com.qualitychemicals.qciss.account.service.UserAccountService;
import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {
    @Autowired
    UserAccountService userAccountService;
    @Autowired SavingsAccountConverter savingsAccountConverter;
    @Autowired SavingsAccountDao savingsAccountDao;

    @Override
    public SavingsAccount getMyAccount() {
        return null;
    }

    @Override
    public SavingsAccount addSavingsAccount(SavingsAccountDto savingsAccountDto) {
        boolean bool =userAccountService.existsByRef(savingsAccountDto.getAccountRef());
        if(bool){
            throw new InvalidValuesException("account already Exists");
        }
        return savingsAccountDao.save(savingsAccountConverter.dtoToEntity(savingsAccountDto));
    }

    @Override
    public SavingsAccount transact(UserAccount userAccount) {
        SavingsAccount savingsAccount =getSavingsAccount(userAccount.getAccountRef());
        if(savingsAccount!=null){
            savingsAccount.setLastTransaction(userAccount.getLastTransaction());
            savingsAccount.setAmount(userAccount.getAmount()+savingsAccount.getAmount());
            return savingsAccountDao.save(savingsAccount);

        }
        throw new ResourceNotFoundException("No such account: "+ userAccount.getAccountRef());
    }

    @Override
    public SavingsAccount getSavingsAccount(String accountRef) {
        return savingsAccountDao.findByAccountRef(accountRef);
    }

    @Override
    public List<SavingsAccount> getAll() {
        return savingsAccountDao.findAll();
    }

    @Override
    public List<SavingsAccount> getByAmountLess(double amount) {
        return savingsAccountDao.findByAmountLessThan(amount);
    }

    @Override
    public List<SavingsAccount> getByAmountGreater(double amount) {
        return savingsAccountDao.findByAmountGreaterThan(amount);
    }

    @Override
    public SavingsAccount updateSavingsAccount(SavingsAccountDto savingsAccountDto) {
        return null;
    }
}
