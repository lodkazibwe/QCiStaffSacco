package com.qualitychemicals.qciss.saccoData.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.saccoData.converter.DeductionScheduleConverter;
import com.qualitychemicals.qciss.saccoData.dao.DeductionScheduleDao;
import com.qualitychemicals.qciss.saccoData.dto.DeductionScheduleDTO;
import com.qualitychemicals.qciss.saccoData.model.DeductionSchedule;
import com.qualitychemicals.qciss.saccoData.model.ScheduleStatus;
import com.qualitychemicals.qciss.saccoData.service.DeductionScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DeductionScheduleServiceImpl implements DeductionScheduleService {
    @Autowired
    DeductionScheduleDao deductionScheduleDao;
    @Autowired DeductionScheduleConverter deductionScheduleConverter;
    @Autowired UserService userService;

    @Override
    @Transactional
    public List<DeductionSchedule> save(
            List<DeductionScheduleDTO> deductionScheduleDtos, String company, Date date) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Date myDate= cal.getTime();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String key =company+year+"-"+month;
        boolean bool =deductionScheduleDao.existsByKey(key);
        if(day<15){
            throw new InvalidValuesException("cannot save schedule before 15th ");
        }

        if(bool){
            throw new InvalidValuesException("can only be saved once a month");
        }
        List<DeductionSchedule> deductionSchedules=deductionScheduleConverter.dtoToEntity(deductionScheduleDtos);
        for(DeductionSchedule deductionSchedule:deductionSchedules){
            deductionSchedule.setCompany(company);
            deductionSchedule.setDate(myDate);
            deductionSchedule.setKey(key);
            deductionSchedule.setStatus(ScheduleStatus.PENDING);
        }
        return deductionScheduleDao.saveAll(deductionSchedules);
    }

    @Override
    public List<DeductionSchedule> update(List<DeductionScheduleDTO> deductionScheduleDtos, String company, Date date) {
        return null;
    }

    @Override
    @Transactional
    public List<DeductionSchedule> get(String company, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String key =company+year+"-"+month;
        return deductionScheduleDao.findByKey(key);
    }



    @Override
    public DeductionSchedule get(int id) {
        return deductionScheduleDao.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Schedule not found "+id));
    }

    @Override
    public void settleSchedule(int id) {
        DeductionSchedule deductionSchedule=get(id);
        deductionSchedule.setStatus(ScheduleStatus.CLEARED);
        deductionScheduleDao.save(deductionSchedule);

    }
}
