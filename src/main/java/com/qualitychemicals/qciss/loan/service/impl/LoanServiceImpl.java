package com.qualitychemicals.qciss.loan.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.loan.converter.LoanConverter;
import com.qualitychemicals.qciss.loan.dao.LoanDao;
import com.qualitychemicals.qciss.loan.dto.LoanDto;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        int rate=loan.getProduct().getInterest();
        int penaltyRate=loan.getProduct().getPenalty();
        double minAmount=loan.getProduct().getMinAmount();
        double maxAmount=loan.getProduct().getMaxAmount();
        int minDuration=loan.getProduct().getMinDuration();
        int maxDuration=loan.getProduct().getMaxDuration();
        if(principal>=minAmount && principal<=maxAmount && duration>=minDuration && duration<=maxDuration){
            loan.setInterest(rate*0.01*principal*duration);
            loan.setPenalty(penaltyRate*0.01*principal);
            loan.setStatus("Pending");
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
    public Loan approve(LoanDto loanDto, int id) {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        //String dat=dateFormat.format(new Date());
        //Date date=dateFormat.parse(dat);
        loanDto.setReleaseDate(date);

        Loan loan=getLoan(id);
        loan.setStatus("Verified");
        loan.setApprovedBy("Admin");
        loan.setDisbursedBy(loanDto.getDisbursedBy());
        loan.setComment(loanDto.getComment());
        return loanDao.save(loan);
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
