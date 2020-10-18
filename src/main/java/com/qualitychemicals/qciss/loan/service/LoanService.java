package com.qualitychemicals.qciss.loan.service;

import com.qualitychemicals.qciss.loan.dto.FeeDto;
import com.qualitychemicals.qciss.loan.dto.LoanDto;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.model.LoanStatus;
import com.qualitychemicals.qciss.loan.model.Product;

import java.util.Date;
import java.util.List;

public interface LoanService {
    Loan request(LoanDto loanDto);
    Loan approve(LoanDto loanDto, int id);
    Loan getLoan(int id);
    void updateStatus(int id, LoanStatus loanStatus);
    void repay(int loanId, double amount);
    List<Loan> getAll();
    List<Loan> findByStatus(String status);
    List<Loan> dueLoans(Date date);
    FeeDto fees(LoanDto loanDto);
}
