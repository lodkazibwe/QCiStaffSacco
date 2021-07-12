package com.qualitychemicals.qciss.account.dao;

import com.qualitychemicals.qciss.account.model.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavingsAccountDao extends JpaRepository<SavingsAccount, Integer> {
    SavingsAccount findByAccountRef(String accountRef);

    List<SavingsAccount> findByAmountLessThan(double amount);

    List<SavingsAccount> findByAmountGreaterThan(double amount);
}
