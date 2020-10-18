package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.converter.LoanTConverter;
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

@RestController
public class LoanTController {
    @Autowired LoanTService loanTService;
    @Autowired LoanTConverter loanTConverter;

    @PutMapping("/release/{loanId}/{transactionType}")//admin
    public ResponseEntity<LoanTDto> releaseLoan(@PathVariable int loanId, @PathVariable TransactionType transactionType){
        LoanT loanT=loanTService.release(loanId, transactionType);
        return new ResponseEntity<>(loanTConverter.entityToDto(loanT), HttpStatus.OK);
    }

    @PostMapping("/mobileRepay")//user
    public ResponseEntity<LoanTDto> repayLoanMobile(@RequestBody LoanTDto loanTDto){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        LoanT loanT=loanTService.repay(loanTDto, userName, TransactionType.MOBILE);
        return new ResponseEntity<>(loanTConverter.entityToDto(loanT), HttpStatus.OK);
    }

    @PostMapping("/bankRepay/{userName}")//admin
    public ResponseEntity<LoanTDto> repayLoanBank(@RequestBody LoanTDto loanTDto, @PathVariable String userName){
        LoanT loanT=loanTService.repay(loanTDto, userName, TransactionType.BANK);
        return new ResponseEntity<>(loanTConverter.entityToDto(loanT), HttpStatus.OK);
    }

    @PostMapping("/cashRepay/{userName}")//admin
    public ResponseEntity<LoanTDto> repayLoanCash(@RequestBody LoanTDto loanTDto, @PathVariable String userName){
        LoanT loanT=loanTService.repay(loanTDto, userName, TransactionType.CASH);
        return new ResponseEntity<>(loanTConverter.entityToDto(loanT), HttpStatus.OK);
    }

}
