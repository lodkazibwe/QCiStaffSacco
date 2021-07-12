package com.qualitychemicals.qciss.account.dao;

import com.qualitychemicals.qciss.account.model.SharesAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharesAccountDao extends JpaRepository<SharesAccount, Integer> {
    SharesAccount findByAccountRef(String accountRef);
    List<SharesAccount> findBySharesGreaterThan(double shares);
    List<SharesAccount> findBySharesLessThan(double shares);

}
