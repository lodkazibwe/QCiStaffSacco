package com.qualitychemicals.qciss.loan.service;

import com.qualitychemicals.qciss.loan.dto.*;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.model.LoanStatus;

import java.util.Date;
import java.util.List;

public interface LoanService {
    Loan request(LoanRequestDto requestDto);
    Loan topUpRequest(LoanRequestDto loanRequestDto, int loanId);
    Loan verify(LoanVerifyDto loanVerifyDto);
    Loan approve(int id);
    Loan getLoan(int id);
    void updateStatus(int id, LoanStatus loanStatus);
    void repay(int loanId, double amount);
    List<Loan> getAll();
    List<Loan> findByStatus(LoanStatus status);
    List<DueLoanDto> dueLoans(Date date);
    List<DueLoanDto> dueLoans(Date date, String borrower);
    FeeDto fees(LoanDto loanDto);
    String deleteMyLoan(int loanId);
    Loan changeStatus(Loan loan, LoanStatus loanStatus);
    List<Loan> myLoans();
    List<DueLoanDto> myDueLoans(Date date);
}
