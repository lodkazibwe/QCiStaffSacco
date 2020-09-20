package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.DAO.WorkDAO;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.model.Work;
import com.qualitychemicals.qciss.profile.rest.v1.ProfileRest;
import com.qualitychemicals.qciss.profile.service.ProfileService;
import com.qualitychemicals.qciss.profile.service.WorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired WorkDAO workDAO;
    @Autowired ProfileService profileService;
    private final Logger logger= LoggerFactory.getLogger(ProfileRest.class);
    @Override
    @Transactional
    public Work updateWork(Work work, int id) {
        logger.info("getting profile...");
        Profile profile =profileService.getProfile(id);
        logger.info("getting current work details...");
        Work currentWork=profile.getWork();
        logger.info("updating...");
        work.setId(currentWork.getId());
        logger.info("saving...");
        /*currentWork.setCompany(work.getCompany());
        currentWork.setScale(work.getScale());
        currentWork.setMonthlySaving(work.getMonthlySaving());
        currentWork.setJob(work.getJob());*/
        return workDAO.save(work);
    }


    @Override
    public Work getWork(int id) {
        return workDAO.findById(id).orElseThrow(()->new ResourceNotFoundException("No details found "+id));
    }

    @Override
    public List<Work> getAll() {
        return workDAO.findAll();
    }

    @Override
    public List<Work> getByCompany(String company) {
        return workDAO.findByCompany(company);
    }

    @Override
    public void deleteWork(int id) {

    }
}
