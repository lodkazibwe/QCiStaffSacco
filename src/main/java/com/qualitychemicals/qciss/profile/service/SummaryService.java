package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.model.Summary;

import java.util.List;

public interface SummaryService {
    Summary updateSummary(Summary summary);
    Summary getSummary(int id);
    Summary getByProfile(int id);
    List<Summary> getAll();
    void deleteSummary(int id);

}
