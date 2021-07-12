package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.LoanAccountDao;
import com.qualitychemicals.qciss.saccoData.model.LoanAccount;
import com.qualitychemicals.qciss.saccoData.service.LoanAccountService;
import com.qualitychemicals.qciss.saccoData.service.SaccoAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanAccountServiceImpl implements LoanAccountService {

    @Autowired LoanAccountDao loanAccountDao;
    @Autowired SaccoAccountService saccoAccountService;
    @Override
    public LoanAccount addLoanAccount(LoanAccount loanAccount) {
        boolean bool =saccoAccountService.existsByName("LOAN-ACCOUNT");
        if(bool){
            return null;
        }
        loanAccount.setName("LOAN-ACCOUNT");
        return loanAccountDao.save(loanAccount);
    }

    @Override
    public LoanAccount updateLoanAccount(LoanAccount update) {
        LoanAccount loanAccount =getLoanAccount();
        loanAccount.setEarlyTopUpCharge(update.getEarlyTopUpCharge()+loanAccount.getEarlyTopUpCharge());
        loanAccount.setExpressHandling(update.getExpressHandling()+loanAccount.getExpressHandling());
        loanAccount.setHandlingCharge(update.getHandlingCharge()+loanAccount.getHandlingCharge());
        loanAccount.setInsuranceFee(update.getInsuranceFee()+loanAccount.getInsuranceFee());
        loanAccount.setInterestReceivable(update.getInterestReceivable()+loanAccount.getInterestReceivable());
        loanAccount.setTransferCharge(update.getTransferCharge()+loanAccount.getTransferCharge());
        loanAccount.setAmount(update.getAmount()+loanAccount.getAmount());
        loanAccount.setAmountIn(update.getAmountIn()+loanAccount.getAmountIn());
        return loanAccountDao.save(loanAccount);
    }

    @Override
    public int getCount() {
        LoanAccount loanAccount= getLoanAccount();
        loanAccount.setCount(loanAccount.getCount()+1);
        updateCount(loanAccount);
        return loanAccount.getCount();

    }
    private void  updateCount(LoanAccount loanAccount){
        loanAccountDao.save(loanAccount);
    }

    @Override
    public LoanAccount getLoanAccount() {
        return loanAccountDao.findByName("LOAN-ACCOUNT");
    }


}
