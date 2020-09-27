package com.qualitychemicals.qciss.loan.dao;

import com.qualitychemicals.qciss.loan.model.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepaymentDao extends JpaRepository<Repayment, Integer> {

}
