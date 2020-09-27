package com.qualitychemicals.qciss.loan.dao;

import com.qualitychemicals.qciss.loan.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao  extends JpaRepository<Payment, Integer> {

}
