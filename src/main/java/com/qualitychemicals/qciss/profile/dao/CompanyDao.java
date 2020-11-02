package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyDao extends JpaRepository<Company, Integer> {
    boolean existsByName(String name);
}
