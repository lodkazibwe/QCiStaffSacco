package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.dao.SummaryDao;
import com.qualitychemicals.qciss.profile.dto.SummaryDto;
import com.qualitychemicals.qciss.profile.model.Summary;
import com.qualitychemicals.qciss.profile.service.SummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryServiceImpl implements SummaryService {
    @Autowired
    SummaryDao summaryDao;
    private final Logger logger= LoggerFactory.getLogger(SummaryServiceImpl.class);
    @Override
    public Summary updateSummary(SummaryDto summaryDto, int id) {
        logger.info("getting current details...");
        Summary summary=getSummary(id);
        logger.info("updating...");
        summary.setPendingFee(summaryDto.getPendingFee());
        summary.setTotalSavings(summaryDto.getTotalSavings());
        summary.setTotalShares(summaryDto.getTotalShares());
        return summaryDao.save(summary);
    }

    @Override
    public Summary getSummary(int id) {
        return summaryDao.findById(id).orElseThrow(()->new ResourceNotFoundException("Not Found: "+id));
    }


    @Override
    public List<Summary> getAll() {
        return summaryDao.findAll();
    }

    @Override
    public void deleteSummary(int id) {

    }
}
