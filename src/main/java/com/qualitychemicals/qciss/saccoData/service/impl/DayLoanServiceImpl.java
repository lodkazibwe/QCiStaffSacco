package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.saccoData.dao.DayLoanDao;
import com.qualitychemicals.qciss.saccoData.model.DayLoan;
import com.qualitychemicals.qciss.saccoData.model.LoanAccount;
import com.qualitychemicals.qciss.saccoData.service.DayLoanService;
import com.qualitychemicals.qciss.saccoData.service.LoanAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DayLoanServiceImpl implements DayLoanService {
    @Autowired DayLoanDao dayLoanDao;
    @Autowired
    LoanAccountService loanAccountService;

    private final Logger logger = LoggerFactory.getLogger(DayLoanServiceImpl.class);

    @Transactional
    @Scheduled(cron = "30 59 23 * * *",zone = "EAT")
    public void addDayLoan() {
        logger.info("getting sacco loan-account...");
        LoanAccount loanAccount= loanAccountService.getLoanAccount();
        DayLoan dayLoan =new DayLoan();
        dayLoan.setBalCf(loanAccount.getAmount());
        dayLoan.setDate(new Date());
        dayLoan.setName(loanAccount.getName());
        dayLoan.setInterestReceivable(loanAccount.getInterestReceivable());
        dayLoan.setPrincipalOutstanding(loanAccount.getPrincipalOutstanding());
        dayLoan.setTransferCharge(loanAccount.getTransferCharge());
        dayLoan.setPrincipalOut(loanAccount.getPrincipalOut());
        dayLoan.setPrincipalIn(loanAccount.getPrincipalIn());
        dayLoan.setInterestIn(loanAccount.getInterestIn());
        dayLoan.setInsuranceFee(loanAccount.getInsuranceFee());
        dayLoan.setHandlingCharge(loanAccount.getHandlingCharge());
        dayLoan.setExpressHandling(loanAccount.getExpressHandling());
        dayLoan.setEarlyTopUpCharge(loanAccount.getEarlyTopUpCharge());
        logger.info("resetting sacco loan-account...");
        loanAccountService.resetLoanAccount(loanAccount);
        logger.info("saving sacco loan-account...");
         dayLoanDao.save(dayLoan);
    }

    @Override
    public DayLoan getDayLoan(Date date) {
        return dayLoanDao.findByDate(date);
    }

    @Override
    public List<DayLoan> getDayLoans(Date dateFrom, Date dateTo) {
        return dayLoanDao.findByDateGreaterThanEqualAndDateLessThanEqual(dateFrom, dateTo);
    }

    private boolean existsByDate(Date date){
        return dayLoanDao.existsByDate(date);
    }
}
