package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.model.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryDao extends JpaRepository<Summary, Integer> {

}
