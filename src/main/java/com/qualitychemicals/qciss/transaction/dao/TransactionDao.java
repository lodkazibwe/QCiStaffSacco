package com.qualitychemicals.qciss.transaction.dao;

import com.qualitychemicals.qciss.transaction.model.LoanT;
import com.qualitychemicals.qciss.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {
    List<LoanT> findByLoanId(int loanId);

    List<Transaction> findByAcctFrom(String acctFrom);
}
