package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.profile.dto.DeductionScheduleDTO;
import com.qualitychemicals.qciss.transaction.dto.LoanTransactionsDto;
import com.qualitychemicals.qciss.transaction.dto.TransactionDto;
import com.qualitychemicals.qciss.transaction.dto.UserTransactionsDto;
import com.qualitychemicals.qciss.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("transaction/")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("/loanTransactions/{loanId}")
    public ResponseEntity<LoanTransactionsDto> loanTransactions(@PathVariable int loanId){
              return  new ResponseEntity<>((transactionService.loanTransactions(loanId)), HttpStatus.OK);

    }

    @GetMapping("/userTransactions/{userName}")
    public ResponseEntity<UserTransactionsDto> userTransactions(@PathVariable String userName){
        return  new ResponseEntity<>(transactionService.userTransactions(userName), HttpStatus.OK);

    }

    @GetMapping("/myloanTransactions/{loanId}")
    public ResponseEntity<LoanTransactionsDto> myLoanTransactions(@PathVariable int loanId){
        return  new ResponseEntity<>((transactionService.myLoanTransactions(loanId)), HttpStatus.OK);

    }

    @GetMapping("/myTransactions")
    public ResponseEntity<UserTransactionsDto> myTransactions(){

        return  new ResponseEntity<>(transactionService.myTransactions(), HttpStatus.OK);

    }


    @PutMapping("/admin/payrollRepayment")
    public ResponseEntity<List<TransactionDto>> payrollRepayment(@RequestBody DeductionScheduleDTO deductionSchedule){

        return null;
    }
}
