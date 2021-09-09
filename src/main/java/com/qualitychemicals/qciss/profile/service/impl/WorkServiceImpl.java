package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.dao.UserDao;
import com.qualitychemicals.qciss.profile.dao.WorkDao;
import com.qualitychemicals.qciss.profile.dto.CompanyUpdateDto;
import com.qualitychemicals.qciss.profile.dto.WorkDto;
import com.qualitychemicals.qciss.profile.model.Status;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.model.Work;
import com.qualitychemicals.qciss.profile.service.UserService;

import com.qualitychemicals.qciss.profile.service.WorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired
    WorkDao workDao;
    @Autowired UserDao userDao;
    @Autowired
    UserService userService;
    private final Logger logger= LoggerFactory.getLogger(WorkServiceImpl.class);
    @Override
    @Transactional
    public Work updateWork(WorkDto workDto) {
        logger.info("getting profile...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting current work details...");
        Profile profile =userService.getProfile(userName);
        if(profile.getStatus()== Status.PENDING) {
            Work work = profile.getWork();
            work.setCompanyName(workDto.getCompanyName());
            work.setEmployeeId(workDto.getEmployeeId());
            work.setToe(workDto.getToe());
            work.setWorkStation(workDto.getWorkStation());
            work.setBasicSalary(workDto.getBasicSalary());
            work.setJob(workDto.getJob());
            profile.setWork(work);
            logger.info("saving...");
            return userDao.save(profile).getWork();
        }
        return profile.getWork();
    }

    @Override
    public Work getWork() {
        logger.info("getting profile...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting current work details...");
        Profile profile =userService.getProfile(userName);
        return profile.getWork();
    }

    @Override
    public Work updatePayrollShares(double amount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user=auth.getName();
        logger.info("getting current work details...");
        Work work=userService.getWorkInfo(user);
        work.setPayrollShares(amount);
        logger.info("saving...");
        return workDao.save(work);
    }

    @Override
    public Work updatePayrollSaving(double amount) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user=auth.getName();
        logger.info("getting current work details...");
        Work work=userService.getWorkInfo(user);
        work.setPayrollSaving(amount);
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
    public Work updateCompanyName(CompanyUpdateDto companyUpdateDto) {
        Work work=getWork(companyUpdateDto.getWorkId());
        work.setCompanyName(companyUpdateDto.getName());
        return workDao.save(work);
    }

    @Override
    public List<Work> getByCompany(String company) {
        return workDao.findByCompanyName(company);
    }

    @Override
    public void deleteWork(int id) {

    }
}
