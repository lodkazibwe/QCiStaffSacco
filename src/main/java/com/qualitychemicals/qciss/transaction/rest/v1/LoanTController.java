package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.dto.LoanPayDto;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@RestController
@RequestMapping("/transaction/loan")
public class LoanTController {
    @Autowired
    LoanTService loanTService;


    @PutMapping("/admin/release")//admin
    public ResponseEntity<LoanTDto> releaseLoan(@Valid @RequestBody LoanPayDto loanPayDto){
        LoanTDto loanTDto=loanTService.release(loanPayDto);
        return new ResponseEntity<>(loanTDto, HttpStatus.OK);
    }

    @PostMapping("/mobileRepay")//profile
    public ResponseEntity<LoanTDto> repayLoanMobile(@Valid @RequestBody LoanPayDto loanPayDto){
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String profile=auth.getName();
        loanTDto.setUserName(profile);*/
        LoanTDto loanTDto=loanTService.repayMobile(loanPayDto);
        return new ResponseEntity<>(loanTDto, HttpStatus.OK);
    }


    @PostMapping("/admin/Repay")//admin
    public ResponseEntity<LoanTDto> repayLoanCash(@Valid @RequestBody LoanTDto loanTDto){

        return new ResponseEntity<>(loanTService.repay(loanTDto), HttpStatus.OK);
    }

}
