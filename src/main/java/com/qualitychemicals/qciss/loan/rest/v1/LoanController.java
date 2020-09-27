package com.qualitychemicals.qciss.loan.rest.v1;

import com.qualitychemicals.qciss.loan.converter.LoanConverter;
import com.qualitychemicals.qciss.loan.dto.LoanDto;
import com.qualitychemicals.qciss.loan.model.Loan;
import com.qualitychemicals.qciss.loan.service.LoanService;
import com.qualitychemicals.qciss.profile.rest.v1.UserController;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("loan")
public class LoanController {
    @Autowired LoanConverter loanConverter;
    @Autowired LoanService loanService;
    private final Logger logger= LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value="Request for a loan")
    @PostMapping("/request")
    public ResponseEntity<LoanDto> request(@RequestBody LoanDto loanDto){
        logger.info("loan request started....");
        Loan loan=loanService.request(loanDto);
        logger.info("request successfully sent pending approval....");
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);
    }

    @ApiOperation(value="Approve loan")
    @PutMapping("/approve/{loanId}")
    public ResponseEntity<LoanDto> approve(@RequestBody LoanDto loanDto, @PathVariable int loanId){
        Loan loan =loanService.approve(loanDto,loanId);
        return new ResponseEntity<>(loanConverter.entityToDto(loan), HttpStatus.OK);
    }

}
