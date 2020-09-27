package com.qualitychemicals.qciss.loan.service;

import com.qualitychemicals.qciss.loan.dto.LoanDto;
import com.qualitychemicals.qciss.loan.model.Loan;

import java.util.Date;
import java.util.List;

public interface LoanService {
    Loan request(LoanDto loanDto);
    Loan approve(LoanDto loanDto, int id);
    Loan getLoan(int id);
    List<Loan> getAll();
    List<Loan> findByStatus(String status);
    List<Loan> dueLoans(Date date);
}
