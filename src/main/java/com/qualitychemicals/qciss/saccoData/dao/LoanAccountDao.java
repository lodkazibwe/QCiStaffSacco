package com.qualitychemicals.qciss.saccoData.dao;

import com.qualitychemicals.qciss.saccoData.model.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanAccountDao extends JpaRepository<LoanAccount, Integer> {
    boolean existsByName(String name);

    LoanAccount findByName(String loan_account);
}
