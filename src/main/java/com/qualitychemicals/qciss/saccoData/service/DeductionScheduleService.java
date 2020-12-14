package com.qualitychemicals.qciss.saccoData.service;

import com.qualitychemicals.qciss.saccoData.dto.DeductionScheduleDTO;
import com.qualitychemicals.qciss.saccoData.model.DeductionSchedule;

import java.util.Date;
import java.util.List;

public interface DeductionScheduleService {
    List<DeductionSchedule> save(List<DeductionScheduleDTO> deductionSchedules, String company, Date date);
    List<DeductionSchedule> update(List<DeductionScheduleDTO> deductionSchedules, String company, Date date);
    List<DeductionSchedule> get(String company,Date date);
    DeductionSchedule get(int id);
    void settleSchedule(int id);

}
