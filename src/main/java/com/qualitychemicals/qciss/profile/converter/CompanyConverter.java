package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.dto.CompanyDto;
import com.qualitychemicals.qciss.profile.model.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyConverter {
    public CompanyDto entityToDto(Company company){
        CompanyDto companyDto= new CompanyDto();
        companyDto.setId(company.getId());
        companyDto.setMinSaving(company.getMinSaving());
        companyDto.setName(company.getName());
        return companyDto;
    }

    public Company dtoToEntity(CompanyDto companyDto){
        Company company= new Company();
        company.setId(companyDto.getId());
        company.setMinSaving(companyDto.getMinSaving());
        company.setName(companyDto.getName());
        return company;
    }

     public List<CompanyDto> entityToDto(List<Company> companies){
        return companies.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Company> dtoToEntity(List<CompanyDto> companyDtos){
        return companyDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
