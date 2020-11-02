package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.converter.CompanyConverter;
import com.qualitychemicals.qciss.profile.dao.CompanyDao;
import com.qualitychemicals.qciss.profile.dto.CompanyDto;
import com.qualitychemicals.qciss.profile.model.Company;
import com.qualitychemicals.qciss.profile.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
   @Autowired
   CompanyDao companyDao;
   @Autowired CompanyConverter companyConverter;
    @Override
    public Company getCompany(int id) {
        return companyDao.findById(id).orElseThrow(()->new ResourceNotFoundException("No such company ID: "+id));
    }

    @Override
    public boolean checkCompany(String name) {
        return companyDao.existsByName(name);
    }

    @Override
    public Company addCompany(CompanyDto companyDto) {
        Company company=companyConverter.dtoToEntity(companyDto);
        return companyDao.save(company);
    }

    @Override
    public Company updateCompany(CompanyDto companyDto) {
        Company company=getCompany(companyDto.getId());
        company.setName(companyDto.getName());
        return companyDao.save(company);
    }
}
