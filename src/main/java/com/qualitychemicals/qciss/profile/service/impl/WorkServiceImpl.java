package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.dao.WorkDao;
import com.qualitychemicals.qciss.profile.dto.WorkDto;
import com.qualitychemicals.qciss.profile.model.Work;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.profile.service.WorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired
    WorkDao workDao;
    @Autowired
    UserService userService;
    private final Logger logger= LoggerFactory.getLogger(WorkServiceImpl.class);
    @Override
    @Transactional
    public Work updateWork(WorkDto workDto, int id) {

        logger.info("getting current work details...");
        Work work= workDao.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No such detail with ID: "+id));
        logger.info("updating...");
        work.setJob(workDto.getJobTittle());
        work.setMonthlySaving(workDto.getMonthlySaving());
        work.setScale(workDto.getSalaryScale());
        work.setCompany(workDto.getCompany());
        logger.info("saving...");
        return workDao.save(work);
    }


    @Override
    public Work getWork(int id) {
        return workDao.findById(id).orElseThrow(()->new ResourceNotFoundException("No details found "+id));
    }

    @Override
    public List<Work> getAll() {
        return workDao.findAll();
    }

    @Override
    public List<Work> getByCompany(String company) {
        return workDao.findByCompany(company);
    }

    @Override
    public void deleteWork(int id) {

    }
}
