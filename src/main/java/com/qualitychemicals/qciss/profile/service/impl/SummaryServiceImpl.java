package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.profile.DAO.SummaryDAO;
import com.qualitychemicals.qciss.profile.model.Summary;
import com.qualitychemicals.qciss.profile.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryServiceImpl implements SummaryService {
    @Autowired
    SummaryDAO summaryDAO;
    @Override
    public Summary updateSummary(Summary summary) {
        return null;
    }

    @Override
    public Summary getSummary(int id) {
        return summaryDAO.findById(id).orElse(null);
    }

    @Override
    public Summary getByProfile(int id) {
        return null;
    }

    @Override
    public List<Summary> getAll() {
        return summaryDAO.findAll();
    }

    @Override
    public void deleteSummary(int id) {

    }
}
