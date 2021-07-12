package com.qualitychemicals.qciss.account.dao;

import com.qualitychemicals.qciss.account.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletDao extends JpaRepository<Wallet, Integer> {

    boolean existsByContact(String contact);

    Wallet findByContact(String contact);

    List<Wallet> findByAmountLessThan(double balance);

    List<Wallet> findByAmountGreaterThan(double balance);

    Wallet findByAccountRef(String accountRef);

}
