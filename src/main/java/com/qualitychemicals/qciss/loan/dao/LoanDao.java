package com.qualitychemicals.qciss.loan.dao;

import com.qualitychemicals.qciss.loan.dto.DueLoanDto;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LoanDao extends JpaRepository<Loan, Integer> {
    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByStatusAndBorrower(LoanStatus status, String borrower);

    Loan findByBorrowerAndId(String borrower, int id);

    @Query("SELECT new com.qualitychemicals.qciss.loan.dto.DueLoanDto(" +
            "l.id,l.loanNumber,l.product.name,l.borrower,l.principal,l.interest,l.penalty, l.totalDue,l.totalPaid," +
            "r.balance,r.balance,r.date,r.date,l.repaymentMode) " +
            "FROM Loan l JOIN l.repayments r WHERE r.balance>0 and r.date<?1 ORDER BY r.date")
    List<DueLoanDto> getDueLoans(Date date);

    List<Loan> findByBorrower(String borrower);

    List<Loan> findByStatusInAndBorrower(List<LoanStatus> loanStatuses, String borrower);

    List<Loan> findByBorrowerAndStatus(String borrower, LoanStatus status);

    @Query("SELECT new com.qualitychemicals.qciss.loan.dto.DueLoanDto(" +
            "l.id,l.loanNumber,l.product.name,l.borrower,l.principal,l.interest,l.penalty, l.totalDue,l.totalPaid," +
            "r.balance,r.balance,r.date,r.date,l.repaymentMode) " +
            "FROM Loan l JOIN l.repayments r WHERE r.balance>0 and r.date<?1 and l.borrower=?2 ORDER BY r.date")
    List<DueLoanDto> getDueLoans(Date date, String borrower);

    @Query("SELECT new com.qualitychemicals.qciss.loan.dto.DueLoanDto(" +
            "l.id,l.loanNumber,l.product.name,l.borrower,l.principal,l.interest,l.penalty, l.totalDue,l.totalPaid," +
            "r.balance,r.balance,r.date,r.date,l.repaymentMode) " +
            "FROM Loan l JOIN l.repayments r WHERE r.balance>0 and l.borrower=?1 ORDER BY r.date")
    List<DueLoanDto> outstandingLoans(String borrower);

    @Query("SELECT new com.qualitychemicals.qciss.loan.dto.DueLoanDto(" +
            "l.id,l.loanNumber,l.product.name,l.borrower,l.principal,l.interest,l.penalty, l.totalDue,l.totalPaid," +
            "r.balance,r.balance,r.date,r.date,l.repaymentMode) " +
            "FROM Loan l JOIN l.repayments r WHERE r.balance>0 and l.status=?1 ORDER BY r.date")
    List<DueLoanDto> outstandingLoans(LoanStatus status);


}

