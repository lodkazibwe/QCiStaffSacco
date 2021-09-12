package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.dto.CumulativeSavingT;
import com.qualitychemicals.qciss.transaction.dto.SavingTDto;
import com.qualitychemicals.qciss.transaction.dto.SavingsTransactionsDto;
import com.qualitychemicals.qciss.transaction.service.SavingTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@CrossOrigin
@RestController
@RequestMapping("/transaction/saving")
public class SavingTController {
    @Autowired
    SavingTService savingTService;

    @PutMapping("/mobile/{amount}")
    public ResponseEntity<SavingTDto> mobileSaving(@PathVariable double amount){
        return new ResponseEntity<>(savingTService.mobileSaving(amount), HttpStatus.OK);

    }
    @PutMapping("/withdraw/{amount}")
    public ResponseEntity<SavingTDto> requestWithdraw(@PathVariable double amount){
        return new ResponseEntity<>(savingTService.withdrawRequest(amount), HttpStatus.OK);

    }

    @GetMapping("/myRecent")
    public ResponseEntity<SavingsTransactionsDto> myRecent(){
        return new ResponseEntity<>(savingTService.myRecent(), HttpStatus.OK);

    }
    @GetMapping("/myAll")
    public ResponseEntity<SavingsTransactionsDto> myAll(){
        return new ResponseEntity<>(savingTService.myAll(), HttpStatus.OK);

    }

    @GetMapping("/cumulative")
    public ResponseEntity<List<CumulativeSavingT>> myAllCumulative(){
        return new ResponseEntity<>(savingTService.myAllCumulative(), HttpStatus.OK);

    }
    @GetMapping("/admin/all/{walletRef}")
    public ResponseEntity<SavingsTransactionsDto> allByWallet(@PathVariable String walletRef){
        return new ResponseEntity<>(savingTService.allByWallet(walletRef), HttpStatus.OK);

    }
    @GetMapping("/admin/recent/{walletRef}")
    public ResponseEntity<SavingsTransactionsDto> recentByWallet(@PathVariable String walletRef){
        return new ResponseEntity<>(savingTService.last5ByWallet(walletRef), HttpStatus.OK);

    }

    /*@GetMapping("/admin/totalSaving/{date}")
    public ResponseEntity<Double> totalSaving(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<>(savingTService.totalSaving(date), HttpStatus.OK);
    }*/

    /*@GetMapping("/admin/totalSaving/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> totalSaving(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                              @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(savingTService.totalSaving(dateFrom, dateTo), HttpStatus.OK);
    }*/

    /*@GetMapping("/admin/dateSaving/{dateFrom}/{dateTo}")
    public ResponseEntity<DateTransactions> dateSaving
            (@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
             @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(savingTService.dateSaving(dateFrom, dateTo), HttpStatus.OK);

    }*/


}
