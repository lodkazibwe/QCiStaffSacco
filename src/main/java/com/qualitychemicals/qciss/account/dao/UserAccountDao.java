package com.qualitychemicals.qciss.account.dao;

import com.qualitychemicals.qciss.account.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountDao extends JpaRepository<UserAccount,Integer> {

    List<UserAccount> findByUserName(String name);

    boolean existsByAccountRef(String ref);

}
