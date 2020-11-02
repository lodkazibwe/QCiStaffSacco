package com.qualitychemicals.qciss.loan.rest.v1;

import com.qualitychemicals.qciss.loan.converter.LoanConverter;
import com.qualitychemicals.qciss.loan.dto.DueLoanDto;
import com.qualitychemicals.qciss.loan.dto.LoanDto;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.service.LoanService;
import com.qualitychemicals.qciss.profile.rest.v1.UserController;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired LoanConverter loanConverter;
    @Autowired LoanService loanService;
    private final Logger logger= LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value="Request for a loan")
    @PostMapping("/request")
    public ResponseEntity<LoanDto> request(@Valid @RequestBody LoanDto loanDto){
        logger.info("loan request started....");
        Loan loan=loanService.request(loanDto);
        logger.info("request successfully sent pending approval....");
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);
    }

    @ApiOperation(value="Approve a loan")
    @PutMapping("/approve/{loanId}")
    public ResponseEntity<LoanDto> approve(@Valid @RequestBody LoanDto loanDto, @PathVariable int loanId){
        Loan loan =loanService.approve(loanDto,loanId);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);
    }

    @GetMapping("/dueLoans/{date}")
    public ResponseEntity<List<DueLoanDto>> DueLoans(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        Date myDate= cal.getTime();
        List<DueLoanDto> dueLoans =loanService.dueLoans(myDate);
        return new ResponseEntity<>(dueLoans, HttpStatus.OK);

    }

}
