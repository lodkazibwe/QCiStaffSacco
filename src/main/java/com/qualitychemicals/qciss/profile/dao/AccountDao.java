package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends JpaRepository<Account, Integer> {

}
