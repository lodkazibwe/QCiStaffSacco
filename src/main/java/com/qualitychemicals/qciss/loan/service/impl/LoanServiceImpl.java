package com.qualitychemicals.qciss.loan.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.loan.converter.LoanConverter;
import com.qualitychemicals.qciss.loan.dao.LoanDao;
import com.qualitychemicals.qciss.loan.dto.FeeDto;
import com.qualitychemicals.qciss.loan.dto.LoanDto;
import com.qualitychemicals.qciss.loan.model.Cycle;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.model.LoanStatus;
import com.qualitychemicals.qciss.loan.model.Repayment;
import com.qualitychemicals.qciss.loan.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LoanServiceImpl  implements LoanService {
    @Autowired LoanConverter loanConverter;
    @Autowired LoanDao loanDao;
    private final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);

    @Override
    public Loan request(LoanDto loanDto) {
        logger.info("processing loan...");
        Loan loan=loanConverter.dtoToEntity(loanDto);
        double principal=loan.getPrincipal();
        int duration=loan.getDuration();

        double minAmount=loan.getProduct().getMinAmount();
        double maxAmount=loan.getProduct().getMaxAmount();
        int minDuration=loan.getProduct().getMinDuration();
        int maxDuration=loan.getProduct().getMaxDuration();
        if(principal>=minAmount && principal<=maxAmount && duration>=minDuration && duration<=maxDuration){
            logger.info("getting user...");
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userName=auth.getName();
            FeeDto fees=fees(loanDto);
            loan.setApplicationFee(fees.getApplicationFee());
            loan.setInterest(fees.getInterest());
            loan.setPenalty(0);
            loan.setBorrower(userName);
            loan.setStatus(LoanStatus.PENDING);
            loan.setRepayments(null);
            loan.setApprovedBy(null);
            loan.setApplicationDate(new Date());
            logger.info("submitting loan...");
            return loanDao.save(loan);
        }else {
            logger.error("invalid values...");
            if(!(principal>=minAmount) && !(principal<=maxAmount) ){
                logger.error("invalid Principal...");
            throw new InvalidValuesException("Principal should be between "+minAmount+" and "+maxAmount);}
            else {
                logger.error("invalid Duration...");
                throw new InvalidValuesException(
                        "Duration should be between "+minDuration+"Months and "+maxDuration+"Months");
            }
        }

    }

    @Override
    public FeeDto fees(LoanDto loanDto) {
        FeeDto fees=new FeeDto();
        logger.info("calculating...");
        Loan loan=loanConverter.dtoToEntity(loanDto);
        int rate=loan.getProduct().getInterest();
        int penaltyRate=loan.getProduct().getPenalty();
        int appRate=loan.getProduct().getApplicationFee();
        double principal=loan.getPrincipal();
        int duration=loan.getDuration();
        fees.setInterest(rate*0.01*principal*duration);
        fees.setPenalty(penaltyRate*0.01*principal);
        fees.setApplicationFee(appRate*0.01*principal);
        logger.info("success...");
        return fees;

    }

    @Override
    public Loan approve(LoanDto loanDto, int id) {
        Date date = new Date();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();

        Loan loan=getLoan(id);
        loan.setReleaseDate(date);
        loan.setStatus(LoanStatus.APPROVED);
        loan.setTotalDue(loan.getPrincipal()+loan.getInterest());
        loan.setTotalPaid(0);
        int duration=loan.getDuration();
        double amount =loan.getTotalDue();
        Date firstDate=loan.getFirstRepaymentDate();
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(firstDate);
        Date releaseDate=loan.getReleaseDate();
        Calendar cal2=Calendar.getInstance();
        cal2.setTime(releaseDate);
        cal2.add(Calendar.MONTH, duration);
        loan.setRepayments(repaymentGen(loan.getRepaymentCycle(),cal1,cal2,amount));
        loan.setApprovedBy(userName);
        loan.setDisbursedBy(loanDto.getDisbursedBy());
        loan.setComment(loanDto.getComment());
        return loanDao.save(loan);
    }

    private List<Repayment> repaymentGen(Cycle cycle, Calendar firstDate, Calendar lastDate, double amount) {
        List<Repayment> repayments=new ArrayList<>();
        List<Date> dates = new ArrayList<>();
        if(cycle==Cycle.MONTHLY){

            for(; firstDate.before(lastDate); firstDate.add(Calendar.MONTH,1)){
                dates.add(firstDate.getTime());
            }

        }else{
            dates.add(lastDate.getTime());
        }
        ///******************/
        double amt = amount / dates.size();
        for(Date date:dates){
            repayments.add(new Repayment(2,date,amt,0,amt,""));
        }
        return repayments;
    }

    @Override
    public void updateStatus(int id, LoanStatus loanStatus) {
        Loan loan=getLoan(id);
        loan.setStatus(loanStatus);
        loanDao.save(loan);
    }

    @Override
    public void repay(int loanId, double amount) {
        Loan loan=getLoan(loanId);
        loan.setTotalDue(loan.getTotalDue()-amount);
        loan.setTotalPaid(loan.getTotalPaid()+amount);
        List<Repayment> repayments=loan.getRepayments();
        double amt=amount;
        int i = 0;
           for(Repayment repayment:repayments){
                if(amt>0 && repayment.getBalance()>0){
                    double value=amt-repayment.getBalance();
                    if(value>=0){
                        repayment.setPaid(repayment.getPaid()+repayment.getBalance());
                        amt=value;
                        repayment.setBalance(0);
                    }else{
                        repayment.setPaid(repayment.getPaid()+amt);
                        repayment.setBalance(repayment.getBalance()-amt);
                        amt=0;
                    }

                }
                repayments.set(i,repayment);

            }
           loan.setRepayments(repayments);
        loanDao.save(loan);

    }

    @Override
    public Loan getLoan(int id) {
        return loanDao.findById(id).orElseThrow(()->new ResourceNotFoundException("No such loan ID: "+id));
    }

    @Override
    public List<Loan> getAll() {
        return loanDao.findAll();
    }

    @Override
    public List<Loan> findByStatus(String status) {

        return loanDao.findByStatus(status);
    }

    @Override
    public List<Loan> dueLoans(Date date) {

        return null;
    }
}
