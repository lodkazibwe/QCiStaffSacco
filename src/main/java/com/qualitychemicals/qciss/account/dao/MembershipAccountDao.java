package com.qualitychemicals.qciss.account.dao;

import com.qualitychemicals.qciss.account.model.MembershipAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipAccountDao extends JpaRepository<MembershipAccount,Integer> {
    MembershipAccount findByAccountRef(String accountRef);
    List<MembershipAccount> findByAmountLessThan(double amount);
    List<MembershipAccount> findByAmountGreaterThan(double amount);

}
