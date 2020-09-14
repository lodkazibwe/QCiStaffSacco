package com.qualitychemicals.qciss.profile.DAO;

import com.qualitychemicals.qciss.profile.model.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryDAO extends JpaRepository<Summary, Integer> {

}
