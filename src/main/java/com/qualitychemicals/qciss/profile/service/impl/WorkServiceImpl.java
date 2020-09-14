package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.profile.DAO.WorkDAO;
import com.qualitychemicals.qciss.profile.model.Work;
import com.qualitychemicals.qciss.profile.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl implements WorkService {
    @Autowired WorkDAO workDAO;
    @Override
    public Work updateWork(Work work) {
        return null;
    }

    @Override
    public Work getWork(int id) {
        return workDAO.findById(id).orElse(null);
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
