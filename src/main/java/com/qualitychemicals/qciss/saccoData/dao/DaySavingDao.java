package com.qualitychemicals.qciss.saccoData.dao;

import com.qualitychemicals.qciss.saccoData.model.DaySaving;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DaySavingDao extends JpaRepository<DaySaving, Integer> {

    boolean existsByDate(Date date);

    DaySaving findByDate(Date date);

    List<DaySaving> findByDateGreaterThanEqualAndDateLessThanEqual(Date dateFrom, Date dateTo);
}
