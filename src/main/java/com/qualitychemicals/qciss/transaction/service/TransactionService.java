package com.qualitychemicals.qciss.transaction.service;

import com.qualitychemicals.qciss.saccoData.dto.DeductionScheduleDTO;
import com.qualitychemicals.qciss.transaction.dto.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

public interface TransactionService {
    AllTransactions myAll();
    AllTransactions myRecent();
    AllTransactions allByWallet(String wallet);
    AllTransactions last5ByWallet(String wallet);

    UserTransactionsDto userTransactions(String userName);
    AllTransactions allTransactions(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1,
                                    @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")Date date2);



    LoanTransactionsDto loanTransactions(int loanId);
    UserTransactionsDto myTransactions();
    LoanTransactionsDto myLoanTransactions(int loanId);
    LoanTransactionsDto loanTransactions();
    AllTransactions allTransactions();
    SavingsTransactionsDto savingTransactions();
    MembershipTransactionsDto membershipTransactions();
    SharesTransactionsDto shareTransactions();
    AllTransactions allByType(TransactionType transactionType);
    List<TransactionDto> scheduleRepayment(DeductionScheduleDTO deductionScheduleDTO);
    List<TransactionDto> scheduleRepayment(List<DeductionScheduleDTO> deductionScheduleDTO);
}
