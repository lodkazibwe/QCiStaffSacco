package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.converter.LoanTConverter;
import com.qualitychemicals.qciss.transaction.dto.LoanReleaseDto;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.model.LoanT;
import com.qualitychemicals.qciss.transaction.model.TransactionType;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class LoanTController {
    @Autowired LoanTService loanTService;
    @Autowired LoanTConverter loanTConverter;

    @PutMapping("/release")//admin
    public ResponseEntity<LoanTDto> releaseLoan(@Valid @RequestBody LoanReleaseDto loanReleaseDto){
        LoanT loanT=loanTService.release(loanReleaseDto.getLoanId(), loanReleaseDto.getTransactionType());
        return new ResponseEntity<>(loanTConverter.entityToDto(loanT), HttpStatus.OK);
    }

    @PostMapping("/mobileRepay")//user
    public ResponseEntity<LoanTDto> repayLoanMobile(@RequestBody LoanTDto loanTDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user=auth.getName();
        loanTDto.setUserName(user);
        LoanT loanT=loanTService.repay(loanTDto, TransactionType.MOBILE);
        return new ResponseEntity<>(loanTConverter.entityToDto(loanT), HttpStatus.OK);
    }


    @PostMapping("/cashRepay")//admin
    public ResponseEntity<LoanTDto> repayLoanCash(@Valid @RequestBody LoanTDto loanTDto){
        LoanT loanT=loanTService.repay(loanTDto, TransactionType.CASH);
        return new ResponseEntity<>(loanTConverter.entityToDto(loanT), HttpStatus.OK);
    }

}
