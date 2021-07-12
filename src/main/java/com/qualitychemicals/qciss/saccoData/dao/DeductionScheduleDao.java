package com.qualitychemicals.qciss.saccoData.dao;

import com.qualitychemicals.qciss.saccoData.model.DeductionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DeductionScheduleDao extends JpaRepository<DeductionSchedule, Integer> {
    List<DeductionSchedule> findByCompanyAndDate(String company, Date date);
    boolean existsByKey(String yearMonth);

    /*@Modifying(clearAutomatically = true)
    @Query("DELETE from DeductionSchedule d WHERE d.key = ?1")
    void deleteDeductionScheduleWithKey(String key);*/

    List<DeductionSchedule> findByKey(String key);
}
