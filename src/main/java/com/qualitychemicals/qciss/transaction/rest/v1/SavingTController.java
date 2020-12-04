package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.dto.SavingTDto;
import com.qualitychemicals.qciss.transaction.service.SavingTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/transaction/saving")
public class SavingTController {
    @Autowired SavingTService savingTService;

    @PutMapping("/mobile/{amount}")
    public ResponseEntity<SavingTDto> mobileSaving(@PathVariable double amount){
        return new ResponseEntity<>(savingTService.mobileSaving(amount), HttpStatus.OK);

    }
}
