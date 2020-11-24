package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankDao extends JpaRepository<Bank, Integer> {

}
