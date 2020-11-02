package com.qualitychemicals.qciss.loan.dao;

import com.qualitychemicals.qciss.loan.dto.DueLoanDto;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.model.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LoanDao extends JpaRepository<Loan, Integer > {
    List<Loan> findByStatus(LoanStatus status);

   @Query("SELECT new com.qualitychemicals.qciss.loan.dto.DueLoanDto(" +
           "l.id,l.borrower,l.principal,l.interest,l.penalty, l.totalDue,l.totalPaid,r.balance) " +
           "FROM Loan l JOIN l.repayments r WHERE r.balance>0 and r.date<?1")
    List<DueLoanDto> getDueLoans(Date date);
   //
}
