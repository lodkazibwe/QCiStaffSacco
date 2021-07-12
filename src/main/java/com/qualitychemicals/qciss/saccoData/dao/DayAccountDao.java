package com.qualitychemicals.qciss.saccoData.dao;

import com.qualitychemicals.qciss.saccoData.model.DayAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DayAccountDao extends JpaRepository<DayAccount, Integer> {
    boolean existsByDateAndName(Date date, String name);

    List<DayAccount> findByDate(Date date);

    List<DayAccount> findByDateGreaterThanEqualAndDateLessThanEqual(Date dateFrom, Date dateTo);

}
