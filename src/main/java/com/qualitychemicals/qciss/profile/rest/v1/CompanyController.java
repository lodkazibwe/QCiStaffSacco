package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.converter.CompanyConverter;
import com.qualitychemicals.qciss.profile.dto.CompanyDto;
import com.qualitychemicals.qciss.profile.model.Company;
import com.qualitychemicals.qciss.profile.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired CompanyService companyService;
    @Autowired CompanyConverter companyConverter;
    private final Logger logger= LoggerFactory.getLogger(CompanyController.class);

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<CompanyDto> addCompany(@Valid @RequestBody CompanyDto companyDto){
        logger.info("adding company...");
        Company company=companyService.addCompany(companyDto);
        return new ResponseEntity<>(companyConverter.entityToDto(company), HttpStatus.OK);

    }

    @Transactional
    @GetMapping("/get/{id}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable int id){
        logger.info("getting company...");
        Company company=companyService.getCompany(id);
        return new ResponseEntity<>(companyConverter.entityToDto(company), HttpStatus.OK);

    }

    @Transactional
    @GetMapping("/update")
    public ResponseEntity<CompanyDto> updateCompany(@Valid @RequestBody CompanyDto companyDto){
        logger.info("updating company...");
        Company company=companyService.updateCompany(companyDto);
        return new ResponseEntity<>(companyConverter.entityToDto(company), HttpStatus.OK);

    }



}
