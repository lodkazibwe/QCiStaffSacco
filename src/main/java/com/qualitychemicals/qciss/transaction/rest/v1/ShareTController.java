package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.dto.CumulativeShareT;
import com.qualitychemicals.qciss.transaction.dto.ShareTDto;
import com.qualitychemicals.qciss.transaction.dto.SharesTransactionsDto;
import com.qualitychemicals.qciss.transaction.service.ShareTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@CrossOrigin
@RestController
@RequestMapping("transaction/shares")
public class ShareTController {
    @Autowired
    ShareTService shareTService;

    @PostMapping("/buy/{amount}")
    public ResponseEntity<ShareTDto> BuyMobile(@PathVariable double amount){
        return new ResponseEntity<>(shareTService.buyShares(amount), HttpStatus.OK);
    }

    @GetMapping("/myRecent")
    public ResponseEntity<SharesTransactionsDto> myRecent(){
        return new ResponseEntity<>(shareTService.myRecent(), HttpStatus.OK);

    }
    @GetMapping("/myAll")
    public ResponseEntity<SharesTransactionsDto> myAll(){
        return new ResponseEntity<>(shareTService.myAll(), HttpStatus.OK);

    }
    @GetMapping("/cumulative")
    public ResponseEntity<List<CumulativeShareT>> myAllCumulative(){
        return new ResponseEntity<>(shareTService.myAllCumulative(), HttpStatus.OK);

    }

    @GetMapping("/admin/all/{walletRef}")
    public ResponseEntity<SharesTransactionsDto> allByWallet(@PathVariable String walletRef){
        return new ResponseEntity<>(shareTService.allByWallet(walletRef), HttpStatus.OK);

    }
    @GetMapping("/admin/recent/{walletRef}")
    public ResponseEntity<SharesTransactionsDto> recentByWallet(@PathVariable String walletRef){
        return new ResponseEntity<>(shareTService.last5ByWallet(walletRef), HttpStatus.OK);

    }

    /*@GetMapping("/admin/totalShares/{date}")
    public ResponseEntity<Double> totalShares(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<>(shareTService.totalShares(date), HttpStatus.OK);
    }*/

    /*@GetMapping("/admin/totalShares/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> totalShares(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                              @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(shareTService.totalShares(dateFrom,dateTo), HttpStatus.OK);
    }*/

    /*@GetMapping("/admin/dateShares/{dateFrom}/{dateTo}")
    public ResponseEntity<DateTransactions> dateShares
            (@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
             @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(shareTService.dateShares(dateFrom, dateTo), HttpStatus.OK);

    }*/


}
