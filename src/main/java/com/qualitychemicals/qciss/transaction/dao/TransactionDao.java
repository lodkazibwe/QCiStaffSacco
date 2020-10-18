package com.qualitychemicals.qciss.transaction.dao;

import com.qualitychemicals.qciss.transaction.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends JpaRepository<Transaction, Integer> {
}
