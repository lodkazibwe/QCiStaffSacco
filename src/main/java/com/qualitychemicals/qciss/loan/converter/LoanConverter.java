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

    public LoanDto entityToDto(Loan loan){
        LoanDto loanDto=new LoanDto();
        loanDto.setApplicationDate(loan.getApplicationDate());
        loanDto.setApprovedBy(loan.getApprovedBy());
        loanDto.setDisbursedBy(loan.getDisbursedBy());
        loanDto.setDuration(loan.getDuration());
        loanDto.setFirstRepaymentDate(loan.getFirstRepaymentDate());
        loanDto.setInterest(loan.getInterest());
        loanDto.setPenalty(loan.getPenalty());
        loanDto.setPrincipal(loan.getPrincipal());
        loanDto.setProductId(loan.getProduct().getId());
        loanDto.setReleaseDate(loan.getReleaseDate());
        loanDto.setRepaymentCycle(loan.getRepaymentCycle());
        loanDto.setStatus(loan.getStatus());
        loanDto.setId(loan.getId());
        loanDto.setBorrowerId(loan.getBorrowerID());
        loanDto.setComment(loan.getComment());
        return loanDto;
    }
    public Loan dtoToEntity(LoanDto loanDto){
        Loan loan=new Loan();
        loan.setFirstRepaymentDate(loanDto.getFirstRepaymentDate());
        loan.setDuration(loanDto.getDuration());
        loan.setDisbursedBy(loanDto.getDisbursedBy());
        loan.setApprovedBy(loanDto.getApprovedBy());
        loan.setApplicationDate(loanDto.getApplicationDate());
        loan.setInterest(loanDto.getInterest());
        loan.setPenalty(loanDto.getPenalty());
        loan.setPrincipal(loanDto.getPrincipal());
        loan.setProduct(productService.getProduct(loanDto.getProductId()));
        loan.setReleaseDate(loanDto.getReleaseDate());
        loan.setStatus(loanDto.getStatus());
        loan.setBorrowerID(loanDto.getBorrowerId());
        loan.setComment(loanDto.getComment());

        return loan;
    }

    public List<LoanDto> entityToDto(List<Loan> loans){
        return loans.stream().map(this::entityToDto).collect(Collectors.toList());
    }
    public List<Loan> dtoToEntity(List<LoanDto> loanDtos){
        return loanDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }


}
