package com.qualitychemicals.qciss.loan.converter;

import com.qualitychemicals.qciss.loan.dto.LoanDto;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.service.ProductService;
import com.qualitychemicals.qciss.profile.converter.UserConverter;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoanConverter {
    @Autowired ProductConverter productConverter;
    @Autowired RepaymentConverter repaymentConverter;
    @Autowired UserConverter userConverter;
    @Autowired UserService userService;
    @Autowired ProductService productService;
    @Autowired SecurityConverter securityConverter;

    public LoanDto entityToDto(Loan loan){
        LoanDto loanDto=new LoanDto();
        loanDto.setApplicationDate(loan.getApplicationDate());
        loanDto.setApprovedBy(loan.getApprovedBy());
        loanDto.setDisbursedBy(loan.getDisbursedBy());
        loanDto.setDuration(loan.getDuration());
        loanDto.setFirstRepaymentDate(loan.getFirstRepaymentDate());
        loanDto.setInterest(loan.getInterest());
        loanDto.setHandlingCharge(loan.getHandlingCharge());
        loanDto.setPenalty(loan.getPenalty());
        loanDto.setPrincipal(loan.getPrincipal());
        loanDto.setProductId(loan.getProduct().getId());
        loanDto.setReleaseDate(loan.getReleaseDate());
        loanDto.setRepaymentCycle(loan.getRepaymentCycle());
        loanDto.setStatus(loan.getStatus());
        loanDto.setTotalDue(loan.getTotalDue());
        loanDto.setTotalPaid(loan.getTotalPaid());
        loanDto.setId(loan.getId());
        loanDto.setBorrower(loan.getBorrower());
        loanDto.setPreparedBy(loan.getPreparedBy());
        loanDto.setSecurityDto(securityConverter.entityToDto(loan.getSecurity()));
        loanDto.setEarlyTopUpCharge(loan.getEarlyTopUpCharge());
        loanDto.setTransferCharge(loan.getTransferCharge());
        loanDto.setInsuranceFee(loan.getInsuranceFee());
        loanDto.setExpressHandling(loan.getExpressHandling());
        loanDto.setRepaymentMode(loan.getRepaymentMode());
        loanDto.setHandlingMode(loan.getHandlingMode());
        loanDto.setTopUpMode(loan.getTopUpMode());
        loanDto.setLoanNumber(loan.getLoanNumber());
        loanDto.setTopUpLoanId(loan.getTopUpLoanId());
        loanDto.setTopUpLoanBalance(loan.getTopUpLoanBalance());
        loanDto.setRemarks(loan.getRemarks());
        return loanDto;
    }
    public Loan dtoToEntity(LoanDto loanDto){
        Loan loan=new Loan();
        loan.setFirstRepaymentDate(loanDto.getFirstRepaymentDate());
        loan.setDuration(loanDto.getDuration());
        loan.setRepaymentCycle(loanDto.getRepaymentCycle());
        loan.setDisbursedBy(loanDto.getDisbursedBy());
        loan.setApprovedBy(loanDto.getApprovedBy());
        loan.setApplicationDate(loanDto.getApplicationDate());
        loan.setInterest(loanDto.getInterest());
        loan.setHandlingCharge(loanDto.getHandlingCharge());
        loan.setPenalty(loanDto.getPenalty());
        loan.setPrincipal(loanDto.getPrincipal());
        loan.setProduct(productService.getProduct(loanDto.getProductId()));
        loan.setReleaseDate(loanDto.getReleaseDate());
        loan.setTotalPaid(loanDto.getTotalPaid());
        loan.setTotalDue(loanDto.getTotalDue());
        loan.setStatus(loanDto.getStatus());
        loan.setBorrower(loanDto.getBorrower());
        loan.setPreparedBy(loanDto.getPreparedBy());
        loan.setSecurity(securityConverter.dtoToEntity(loanDto.getSecurityDto()));
        loan.setEarlyTopUpCharge(loanDto.getEarlyTopUpCharge());
        loan.setTransferCharge(loanDto.getTransferCharge());
        loan.setTopUpMode(loanDto.getTopUpMode());
        loan.setInsuranceFee(loanDto.getInsuranceFee());
        loan.setExpressHandling(loanDto.getExpressHandling());
        loan.setRepaymentMode(loanDto.getRepaymentMode());
        loan.setHandlingMode(loanDto.getHandlingMode());
        loan.setTopUpMode(loanDto.getTopUpMode());
        loan.setLoanNumber(loanDto.getLoanNumber());
        loan.setRemarks(loanDto.getRemarks());
        loan.setTopUpLoanBalance(loanDto.getTopUpLoanBalance());
        loan.setTopUpLoanId(loanDto.getTopUpLoanId());
        return loan;
    }

    public List<LoanDto> entityToDto(List<Loan> loans){
        return loans.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Loan> dtoToEntity(List<LoanDto> loanDtos){
        return loanDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }


}
