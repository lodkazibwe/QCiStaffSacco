package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.dto.CompanyUpdateDto;
import com.qualitychemicals.qciss.profile.dto.WorkDto;
import com.qualitychemicals.qciss.profile.model.Work;

import java.util.List;

public interface WorkService {
    Work updateWork(WorkDto workDto);
    Work updateCompanyName(CompanyUpdateDto companyUpdateDto);
    Work getWork(int id);
    List<Work> getAll();
    List<Work> getByCompany(String company);
    void deleteWork(int id);
}
