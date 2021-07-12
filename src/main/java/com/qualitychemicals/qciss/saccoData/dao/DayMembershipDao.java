package com.qualitychemicals.qciss.saccoData.dao;

import com.qualitychemicals.qciss.saccoData.model.DayMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DayMembershipDao extends JpaRepository<DayMembership, Integer> {
    boolean existsByDate(Date date);

    DayMembership findByDate(Date date);

    List<DayMembership> findByDateGreaterThanEqualAndDateLessThanEqual(Date dateFrom, Date dateTo);
}
