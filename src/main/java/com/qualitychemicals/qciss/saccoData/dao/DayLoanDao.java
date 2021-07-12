package com.qualitychemicals.qciss.saccoData.dao;

import com.qualitychemicals.qciss.saccoData.model.DayLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DayLoanDao extends JpaRepository<DayLoan, Integer> {

    boolean existsByDate(Date date);

    DayLoan findByDate(Date date);

    List<DayLoan> findByDateGreaterThanEqualAndDateLessThanEqual(Date dateFrom, Date dateTo);

}
