package com.qualitychemicals.qciss.transaction.rest.v1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qualitychemicals.qciss.transaction.dto.AllTransactions;
import com.qualitychemicals.qciss.transaction.service.MembershipTService;
import com.qualitychemicals.qciss.transaction.service.SavingTService;
import com.qualitychemicals.qciss.transaction.service.ShareTService;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@CrossOrigin
@RestController
@RequestMapping("transaction/")
public class TransactionController {
    @Autowired
    TransactionService transactionService;
    @Autowired ShareTService shareTService;
    @Autowired SavingTService savingTService;
    @Autowired MembershipTService membershipTService;


    @GetMapping("/myRecent")
    public ResponseEntity<AllTransactions> myRecent(){
        return new ResponseEntity<>(transactionService.myRecent(), HttpStatus.OK);

    }
    @GetMapping("/myTransactions")
    public ResponseEntity<AllTransactions> myAll(){
        return new ResponseEntity<>(transactionService.myAll(), HttpStatus.OK);

    }
    @GetMapping("/admin/all/{walletRef}")
    public ResponseEntity<AllTransactions> allByWallet(@PathVariable String walletRef){
        return new ResponseEntity<>(transactionService.allByWallet(walletRef), HttpStatus.OK);

    }
    @GetMapping("/admin/recent/{walletRef}")
    public ResponseEntity<AllTransactions> recentByWallet(@PathVariable String walletRef){
        return new ResponseEntity<>(transactionService.last5ByWallet(walletRef), HttpStatus.OK);

    }

    @GetMapping("/admin/all/{dateFrom}/{dateTo}")
    public ResponseEntity<AllTransactions> allTransactions(@PathVariable @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", locale = "pt-BR", timezone = "EAT") Date dateFrom,
                                                           @PathVariable @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", locale = "pt-BR", timezone = "EAT")Date dateTo){
        return new ResponseEntity<>(transactionService.allTransactions(dateFrom, dateTo), HttpStatus.OK);

    }

    /*@GetMapping("/admin/loanTransactions/{loanId}")
    public ResponseEntity<LoanTransactionsDto> loanTransactions(@PathVariable int loanId){
              return  new ResponseEntity<>((transactionService.loanTransactions(loanId)), HttpStatus.OK);

    }*/
    /*@GetMapping("/admin/getAll")
    public ResponseEntity<AllTransactions> allTransactions(){
       return new ResponseEntity<>(transactionService.allTransactions(), HttpStatus.OK);

    }*//*
    @GetMapping("/admin/getAll/{transactionType}")
    public ResponseEntity<AllTransactions> allByType(@PathVariable TransactionType transactionType){
        return new ResponseEntity<>(transactionService.allByType(transactionType), HttpStatus.OK);

    }

    @GetMapping("/admin/loanTransactions")
    public ResponseEntity<LoanTransactionsDto> allLoanTransactions(){
        return new ResponseEntity<>(transactionService.loanTransactions(), HttpStatus.OK);

    }
    @GetMapping("/admin/savingTransactions/all")
    public ResponseEntity<SavingsTransactionsDto> savingTransactions(){
        return new ResponseEntity<>(transactionService.savingTransactions(), HttpStatus.OK);

    }
    @GetMapping("/admin/membershipTransactions/all")
    public ResponseEntity<MembershipTransactionsDto> membershipTransactions(){
        return new ResponseEntity<>(transactionService.membershipTransactions(), HttpStatus.OK);

    }

    @GetMapping("/admin/shareTransactions/all")
    public ResponseEntity<SharesTransactionsDto> shareTransactions(){
        return new ResponseEntity<>(transactionService.shareTransactions(), HttpStatus.OK);

    }
    @GetMapping("/admin/userTransactions/{userName}")
    public ResponseEntity<UserTransactionsDto> userTransactions(@PathVariable String userName){
        return  new ResponseEntity<>(transactionService.userTransactions(userName), HttpStatus.OK);

    }*/

    /*@GetMapping("/myLoanTransactions/{loanId}")
    public ResponseEntity<LoanTransactionsDto> myLoanTransactions(@PathVariable int loanId){
        return  new ResponseEntity<>((transactionService.myLoanTransactions(loanId)), HttpStatus.OK);

    }*/


    /*@GetMapping("/myTransactions")
    public ResponseEntity<UserTransactionsDto> myTransactions(){
        return  new ResponseEntity<>(transactionService.myTransactions(), HttpStatus.OK);

    }*/


    /*@PutMapping("/admin/payrollRepayment")
    public ResponseEntity<List<TransactionDto>> payrollRepayment(@RequestBody DeductionScheduleDTO deductionSchedule){
        return new ResponseEntity<>(transactionService.scheduleRepayment(deductionSchedule),HttpStatus.OK);
    }


    @GetMapping("/shareTransactions")
    public ResponseEntity<SharesTransactionsDto> myShareTransactions(){
        return new ResponseEntity<>(shareTService.shareTransactions(), HttpStatus.OK);

    }
    @GetMapping("/savingTransactions")
    public ResponseEntity<SavingsTransactionsDto> mySavingTransactions(){
        return new ResponseEntity<>(savingTService.savingTransactions(), HttpStatus.OK);

    }

    @GetMapping("/membershipTransactions")
    public ResponseEntity<MembershipTransactionsDto> myMembershipTransactions(){
        return new ResponseEntity<>(membershipTService.membershipTransactions(), HttpStatus.OK);

    }*/

}

