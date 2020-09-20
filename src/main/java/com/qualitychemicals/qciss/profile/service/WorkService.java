package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.model.Work;

import java.util.List;

public interface WorkService {
    Work updateWork(Work work, int id);
    Work getWork(int id);
    List<Work> getAll();
    List<Work> getByCompany(String company);
    void deleteWork(int id);

}
