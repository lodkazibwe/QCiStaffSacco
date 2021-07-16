package com.qualitychemicals.qciss.saccoData.dao;

import com.qualitychemicals.qciss.saccoData.model.ExternalAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalAccountDao extends JpaRepository<ExternalAccount, Integer> {

    boolean existsByName(String name);

    ExternalAccount findByName(String name);

}
