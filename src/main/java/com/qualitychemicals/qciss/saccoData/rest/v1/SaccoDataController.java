package com.qualitychemicals.qciss.saccoData.rest.v1;

import com.qualitychemicals.qciss.saccoData.model.*;
import com.qualitychemicals.qciss.saccoData.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/data")
public class SaccoDataController {
    @Autowired LoanAccountService loanAccountService;
    @Autowired MembershipService membershipService;
    @Autowired SavingService savingService;
    @Autowired ShareService shareService;
    @Autowired DayLoanService dayLoanService;
    @Autowired DayMembershipService dayMembershipService;
    @Autowired DaySavingService daySavingService;
    @Autowired DayShareService dayShareService;
    @Autowired SaccoAccountService saccoAccountService;
    @Autowired DayAccountService dayAccountService;


    @GetMapping("/loanAccount")
    public ResponseEntity<LoanAccount> saccoLoanAccount(){
        return new ResponseEntity<> (loanAccountService.getLoanAccount(), HttpStatus.OK);
    }

    @GetMapping("/loanAccount/{date}")
    public ResponseEntity<DayLoan> getDayLoansData(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<> (dayLoanService.getDayLoan(date), HttpStatus.OK);
    }

    @GetMapping("/loanAccount/{dateFrom}/{dateTo}")
    public ResponseEntity<List<DayLoan>> getDayLoansData(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<> (dayLoanService.getDayLoans(dateFrom, dateTo), HttpStatus.OK);
    }


    @GetMapping("/membershipAccount")
    public ResponseEntity<Membership> saccoMembershipAccount(){
        return new ResponseEntity<> (membershipService.getMembership(), HttpStatus.OK);
    }

    @GetMapping("/membershipAccount/{date}")
    public ResponseEntity<DayMembership> getDayMembershipData(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<> (dayMembershipService.getDayMembership(date), HttpStatus.OK);
    }

    @GetMapping("/membershipAccount/{dateFrom}/{dateTo}")
    public ResponseEntity<List<DayMembership>> getDayMembershipData(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<> (dayMembershipService.getDayMemberships(dateFrom, dateTo), HttpStatus.OK);
    }

    @GetMapping("/savingAccount")
    public ResponseEntity<Saving> saccoSavingAccount(){
        return new ResponseEntity<> (savingService.getSavingInfo(), HttpStatus.OK);
    }

    @GetMapping("/savingAccount/{date}")
    public ResponseEntity<DaySaving> getDaySavingData(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<> (daySavingService.getDaySaving(date), HttpStatus.OK);
    }

    @GetMapping("/savingAccount/{dateFrom}/{dateTo}")
    public ResponseEntity<List<DaySaving>> getDaySavingData(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<> (daySavingService.getDaySavings(dateFrom, dateTo), HttpStatus.OK);
    }

    @GetMapping("/shareAccount")
    public ResponseEntity<Share> saccoShareAccount(){
        return new ResponseEntity<> (shareService.getShareInfo(), HttpStatus.OK);
    }

    @GetMapping("/shareAccount/{date}")
    public ResponseEntity<DayShare> getDayShareData(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<> (dayShareService.getDayShare(date), HttpStatus.OK);
    }

    @GetMapping("/shareAccount/{dateFrom}/{dateTo}")
    public ResponseEntity<List<DayShare>> getDayShareData(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<> (dayShareService.getDayShares(dateFrom, dateTo), HttpStatus.OK);
    }

    @GetMapping("/allSaccoAccounts")
    public ResponseEntity<List<SaccoAccount>> allSaccoAccounts(){
        return new ResponseEntity<> (saccoAccountService.getAllAccounts(), HttpStatus.OK);
    }

    @GetMapping("/allDayAccounts/{date}")
    public ResponseEntity<List<DayAccount>> allSaccoAccounts(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<> (dayAccountService.getDayAccount(date), HttpStatus.OK);
    }



}
