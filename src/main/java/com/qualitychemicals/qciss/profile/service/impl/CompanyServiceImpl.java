package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.InvalidValuesException;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.converter.CompanyConverter;
import com.qualitychemicals.qciss.profile.dao.CompanyDao;
import com.qualitychemicals.qciss.profile.dto.CompanyDto;
import com.qualitychemicals.qciss.profile.model.Company;
import com.qualitychemicals.qciss.profile.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {
   @Autowired
   CompanyDao companyDao;
   @Autowired CompanyConverter companyConverter;
    private final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);
    @Override
    public Company getCompany(int id) {
        return companyDao.findById(id).orElseThrow(()->new ResourceNotFoundException("No such company ID: "+id));
    }

    @Override
    public boolean checkCompany(String name) {

        logger.info("checking company...");
        return companyDao.existsByName(name);
    }

    @Override
    public Company addCompany(CompanyDto companyDto) {
        logger.info("converting...");
        boolean bool =companyDao.existsByName(companyDto.getName());
        if(bool){
            logger.info("company already exists...");
            throw new InvalidValuesException("company already exists");
        }
        Company company=companyConverter.dtoToEntity(companyDto);
        logger.info("saving...");
    return companyDao.save(company);
    }

    @Override
    public Company updateCompany(CompanyDto companyDto) {
        logger.info("getting company...");
        Company company=getCompany(companyDto.getId());
        logger.info("updating company...");
        company.setName(companyDto.getName());
        return companyDao.save(company);
    }
}
