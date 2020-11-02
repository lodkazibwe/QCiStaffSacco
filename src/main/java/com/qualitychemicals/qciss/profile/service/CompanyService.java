package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.dto.CompanyDto;
import com.qualitychemicals.qciss.profile.model.Company;

public interface CompanyService {
    Company addCompany(CompanyDto companyDto);
    Company getCompany(int id);
    Company updateCompany(CompanyDto companyDto);
    boolean checkCompany(String name);
}
