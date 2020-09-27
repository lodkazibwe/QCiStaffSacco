package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.dto.SummaryDto;
import com.qualitychemicals.qciss.profile.model.Summary;

import java.util.List;

public interface SummaryService {
    Summary updateSummary(SummaryDto summaryDto, int id);
    Summary getSummary(int id);
    List<Summary> getAll();
    void deleteSummary(int id);
}
