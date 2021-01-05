package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.dto.DateTransactions;
import com.qualitychemicals.qciss.transaction.dto.LoanPayDto;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.service.LoanTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@CrossOrigin
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

    @GetMapping("/admin/totalRepayment/{date}")
    public ResponseEntity<Double> totalRepayment(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<>(loanTService.totalRepay(date), HttpStatus.OK);
    }

    @GetMapping("/admin/totalRepayment/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> totalRepayment(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                                  @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(loanTService.totalRepay(dateFrom,dateTo), HttpStatus.OK);
    }

    @GetMapping("/admin/dateRepayment/{dateFrom}/{dateTo}")
    public ResponseEntity<DateTransactions> dateRepayment
            (@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
             @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(loanTService.dateRepay(dateFrom, dateTo), HttpStatus.OK);

    }

}
