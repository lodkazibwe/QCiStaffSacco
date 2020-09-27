package com.qualitychemicals.qciss.loan.dao;

import com.qualitychemicals.qciss.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanDao extends JpaRepository<Loan, Integer > {
    List<Loan> findByStatus(String status);
}
