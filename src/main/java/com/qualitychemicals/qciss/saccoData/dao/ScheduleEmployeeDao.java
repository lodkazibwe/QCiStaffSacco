package com.qualitychemicals.qciss.saccoData.dao;

import com.qualitychemicals.qciss.saccoData.model.ScheduleEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleEmployeeDao extends JpaRepository<ScheduleEmployee, Integer> {
}
