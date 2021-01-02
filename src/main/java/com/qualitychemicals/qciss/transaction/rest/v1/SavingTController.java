package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.dto.DateTransactions;
import com.qualitychemicals.qciss.transaction.dto.SavingTDto;
import com.qualitychemicals.qciss.transaction.service.SavingTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@RestController
@RequestMapping("/transaction/saving")
public class SavingTController {
    @Autowired
    SavingTService savingTService;

    @PutMapping("/mobile/{amount}")
    public ResponseEntity<SavingTDto> mobileSaving(@PathVariable double amount){
        return new ResponseEntity<>(savingTService.mobileSaving(amount), HttpStatus.OK);

    }

    @GetMapping("/admin/totalSaving/{date}")
    public ResponseEntity<Double> totalSaving(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<>(savingTService.totalSaving(date), HttpStatus.OK);
    }

    @GetMapping("/admin/totalSaving/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> totalSaving(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                              @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(savingTService.totalSaving(dateFrom, dateTo), HttpStatus.OK);
    }

    @GetMapping("/admin/dateSaving/{dateFrom}/{dateTo}")
    public ResponseEntity<DateTransactions> dateSaving
            (@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
             @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(savingTService.dateSaving(dateFrom, dateTo), HttpStatus.OK);

    }
}
