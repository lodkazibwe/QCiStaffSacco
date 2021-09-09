package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.converter.BankConverter;
import com.qualitychemicals.qciss.profile.dto.BankDto;
import com.qualitychemicals.qciss.profile.model.Bank;
import com.qualitychemicals.qciss.profile.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/bank")
public class BankController {
    @Autowired BankService bankService;
    @Autowired BankConverter bankConverter;
    private final Logger logger= LoggerFactory.getLogger(CompanyController.class);

    @PutMapping("/add")
    public ResponseEntity<BankDto> addBank(@Valid @RequestBody BankDto bankDto){
        logger.info("adding bank...");
        Bank bank=bankService.addBank(bankDto);
        return new ResponseEntity<>(bankConverter.entityToDto(bank), HttpStatus.OK);

    }

    @PutMapping("/get")
    public ResponseEntity<BankDto> myBank(){
        Bank bank=bankService.myBank();
        return new ResponseEntity<>(bankConverter.entityToDto(bank), HttpStatus.OK);
    }


}
