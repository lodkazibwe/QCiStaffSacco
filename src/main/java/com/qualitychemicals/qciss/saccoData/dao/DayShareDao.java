package com.qualitychemicals.qciss.saccoData.dao;

import com.qualitychemicals.qciss.saccoData.model.DayShare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DayShareDao extends JpaRepository<DayShare, Integer> {
    boolean existsByDate(Date date);

    DayShare findByDate(Date date);

    List<DayShare> findByDateGreaterThanEqualAndDateLessThanEqual(Date dateFrom, Date dateTo);
}
