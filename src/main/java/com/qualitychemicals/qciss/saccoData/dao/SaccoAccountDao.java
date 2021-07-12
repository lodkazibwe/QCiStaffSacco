package com.qualitychemicals.qciss.saccoData.dao;

import com.qualitychemicals.qciss.saccoData.model.SaccoAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaccoAccountDao extends JpaRepository<SaccoAccount, Integer> {
    boolean existsByName(String name);

    SaccoAccount findByName(String name);

}
