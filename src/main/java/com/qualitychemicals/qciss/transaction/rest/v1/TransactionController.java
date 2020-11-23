package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.profile.dto.DeductionScheduleDTO;
import com.qualitychemicals.qciss.transaction.converter.LoanTConverter;
import com.qualitychemicals.qciss.transaction.converter.TransactionConverter;
import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.dto.TransactionDto;
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
    @Autowired LoanTConverter loanTConverter;
    @Autowired TransactionConverter transactionConverter;

    @GetMapping("/loanTransactions/{loanId}")
    public ResponseEntity<List<LoanTDto>> loanTransactions(@PathVariable int loanId){
              return  new ResponseEntity<>(loanTConverter.entityToDto
                (transactionService.loanTransactions(loanId)), HttpStatus.OK);

    }

    @GetMapping("/userTransactions/{uid}")
    public ResponseEntity<List<TransactionDto>> userTransactions(@PathVariable int uid){
        return  new ResponseEntity<>(transactionConverter.entityToDto
                (transactionService.userTransactions(uid)), HttpStatus.OK);

    }
    @PutMapping("/admin/payrollRepayment")
    public ResponseEntity<List<TransactionDto>> payrollRepayment(@RequestBody DeductionScheduleDTO deductionSchedule){

        return null;
    }
}
