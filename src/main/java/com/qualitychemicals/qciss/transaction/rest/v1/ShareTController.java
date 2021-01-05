package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.dto.DateTransactions;
import com.qualitychemicals.qciss.transaction.dto.ShareTDto;
import com.qualitychemicals.qciss.transaction.dto.SharesTransactionsDto;
import com.qualitychemicals.qciss.transaction.service.ShareTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@CrossOrigin
@RestController
@RequestMapping("transaction/shares")
public class ShareTController {
    @Autowired
    ShareTService shareTService;

    @PostMapping("/buy/{amount}")
    public ResponseEntity<ShareTDto> BuyMobile(@PathVariable double amount){
        return new ResponseEntity<>(shareTService.mobileShares(amount), HttpStatus.OK);
    }

    @GetMapping("/admin/totalShares/{date}")
    public ResponseEntity<Double> totalShares(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<>(shareTService.totalShares(date), HttpStatus.OK);
    }

    @GetMapping("/admin/totalShares/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> totalShares(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                              @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(shareTService.totalShares(dateFrom,dateTo), HttpStatus.OK);
    }

    @GetMapping("/admin/dateShares/{dateFrom}/{dateTo}")
    public ResponseEntity<DateTransactions> dateShares
            (@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
             @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(shareTService.dateShares(dateFrom, dateTo), HttpStatus.OK);

    }


}
