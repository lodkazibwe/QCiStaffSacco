package com.qualitychemicals.qciss.loan.rest.v1;

import com.qualitychemicals.qciss.loan.converter.LoanConverter;
import com.qualitychemicals.qciss.loan.dto.*;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.model.LoanStatus;
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
import java.util.Date;
import java.util.List;


@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired LoanConverter loanConverter;
    @Autowired LoanService loanService;
    private final Logger logger= LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value="Request for a loan")
    @PostMapping("/request")
    public ResponseEntity<LoanDto> request(@Valid @RequestBody LoanRequestDto loanRequestDto){
        logger.info("loan request started....");
        Loan loan=loanService.request(loanRequestDto);
        logger.info("request successfully sent pending approval....");
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);
    }

    @PostMapping("/topUpRequest/{loanId}")
    public ResponseEntity<LoanDto> topUpRequest(@Valid @RequestBody LoanRequestDto loanRequestDto, @PathVariable int loanId){
        logger.info("loan request started....");
        Loan loan=loanService.topUpRequest(loanRequestDto, loanId);
        logger.info("request successfully sent pending approval....");
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);
    }

    @ApiOperation(value="verify a loan")
    @PutMapping("/admin/verify")
    public ResponseEntity<LoanDto> verify(@Valid @RequestBody LoanVerifyDto loanVerifyDto){
        Loan loan =loanService.verify(loanVerifyDto);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);
    }

    @ApiOperation(value="Approve a loan")
    @PutMapping("/admin/approve/{loanId}")
    public ResponseEntity<LoanDto> approve(@PathVariable int loanId){
        Loan loan =loanService.approve(loanId);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);
    }

    @ApiOperation(value="reject a loan")
    @PutMapping("/admin/reject")
    public ResponseEntity<LoanDto> reject(@RequestBody LoanRejectDto loanRejectDto){
        Loan loan =loanService.reject(loanRejectDto);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);
    }

    @GetMapping("/admin/dueLoans/{date}")
    public ResponseEntity<List<DueLoanDto>> dueLoans(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        List<DueLoanDto> dueLoans =loanService.dueLoans(date);
        return new ResponseEntity<>(dueLoans, HttpStatus.OK);

    }

    @GetMapping("/admin/userDueLoans/{userName}/{date}")
    public ResponseEntity<List<DueLoanDto>> userDueLoans(@PathVariable String userName, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        List<DueLoanDto> dueLoans =loanService.dueLoans(date, userName);
        return new ResponseEntity<>(dueLoans, HttpStatus.OK);
    }

    @GetMapping("/admin/outstandingLoans/{userName}")
    public ResponseEntity<List<DueLoanDto>> userOutstandingLoans(@PathVariable String userName){
        List<DueLoanDto> dueLoans =loanService.outstandingLoans(userName);
        return new ResponseEntity<>(dueLoans, HttpStatus.OK);
    }

    @GetMapping("/admin/outstandingLoans")
    public ResponseEntity<List<DueLoanDto>> outstandingLoans(){
        List<DueLoanDto> dueLoans =loanService.outstandingLoans();
        return new ResponseEntity<>(dueLoans, HttpStatus.OK);
    }

    @DeleteMapping("/deleteLoanRequest/{loanId}")
    public ResponseEntity<String> deleteLoanRequest(@PathVariable int loanId){
        return new ResponseEntity<>(loanService.deleteMyLoan(loanId), HttpStatus.OK);
    }

    @GetMapping("/myLoans")
    public ResponseEntity<List<LoanDto>> myLoans(){
        List<Loan> loan =loanService.myLoans();
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);

    }
    @GetMapping("/myTotalOutstanding")
    public ResponseEntity<Double> myTotalDue(){
        return new ResponseEntity<>(loanService.myTotalDue(), HttpStatus.OK);

    }

    @GetMapping("/admin/loanRequests")
    public ResponseEntity<List<LoanDto>> loanRequests(){
        List<Loan> loan =loanService.getLoan(LoanStatus.PENDING);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);

    }
    @GetMapping("/admin/openLoans")
    public ResponseEntity<List<LoanDto>> openLoans(){
        List<Loan> loan =loanService.getLoan(LoanStatus.OPEN);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);

    }

    @GetMapping("/admin/checkedLoans")
    public ResponseEntity<List<LoanDto>> checkedLoans(){
        List<Loan> loan =loanService.getLoan(LoanStatus.CHECKED);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);

    }

    @GetMapping("/admin/approvedLoans")
    public ResponseEntity<List<LoanDto>> approvedLoans(){
        List<Loan> loan =loanService.getLoan(LoanStatus.APPROVED);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);

    }

    @GetMapping("/admin/passedMaturityLoans")
    public ResponseEntity<List<LoanDto>> passedMaturityLoans(){
        List<Loan> loan =loanService.getLoan(LoanStatus.PASSED_MATURITY);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);

    }

    @GetMapping("/admin/closedLoans")
    public ResponseEntity<List<LoanDto>> closedLoans(){
        List<Loan> loan =loanService.getLoan(LoanStatus.CLOSED);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);

    }

    @GetMapping("/admin/rejectedLoans")
    public ResponseEntity<List<LoanDto>> rejectedLoans(){
        List<Loan> loan =loanService.getLoan(LoanStatus.REJECTED);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);

    }

    @GetMapping("/admin/getLoanAppraisal/{loanId}")
    public ResponseEntity<AppraisalSheetDto> getLoanAppraisal(@PathVariable int loanId){
        return new ResponseEntity<>(loanService.getLoanAppraisal(loanId), HttpStatus.OK);

    }


    @GetMapping("/myDueLoans/{date}")
    public ResponseEntity<List<DueLoanDto>> myDueLoans(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        //List<Loan> loan =loanService.myDueLoans(date);
        return new ResponseEntity<>(loanService.myDueLoans(date), HttpStatus.OK);

    }

    @GetMapping("/myOutstanding")
    public ResponseEntity<List<DueLoanDto>> myOutstandingLoans(){
        return new ResponseEntity<>(loanService.myOutstandingLoans(), HttpStatus.OK);

    }



}
