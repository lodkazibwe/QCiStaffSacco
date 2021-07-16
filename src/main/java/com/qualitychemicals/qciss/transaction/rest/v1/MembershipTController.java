package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.dto.MembershipTDto;
import com.qualitychemicals.qciss.transaction.dto.MembershipTransactionsDto;
import com.qualitychemicals.qciss.transaction.service.MembershipTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@CrossOrigin
@RestController
@RequestMapping("/transaction/membership")
public class MembershipTController {
    @Autowired
    MembershipTService membershipTService;

    @PutMapping("/pay/{amount}")
    public ResponseEntity<MembershipTDto> payMembership(@PathVariable double amount){
        return new ResponseEntity<>(membershipTService.payMembership(amount), HttpStatus.OK);
    }

    @GetMapping("/myRecent")
    public ResponseEntity<MembershipTransactionsDto> myRecent(){
        return new ResponseEntity<>(membershipTService.myRecent(), HttpStatus.OK);

    }
    @GetMapping("/myAll")
    public ResponseEntity<MembershipTransactionsDto> myAll(){
        return new ResponseEntity<>(membershipTService.myAll(), HttpStatus.OK);

    }
    @GetMapping("/admin/all/{walletRef}")
    public ResponseEntity<MembershipTransactionsDto> allByWallet(@PathVariable String walletRef){
        return new ResponseEntity<>(membershipTService.allByWallet(walletRef), HttpStatus.OK);

    }
    @GetMapping("/admin/recent/{walletRef}")
    public ResponseEntity<MembershipTransactionsDto> recentByWallet(@PathVariable String walletRef){
        return new ResponseEntity<>(membershipTService.last5ByWallet(walletRef), HttpStatus.OK);

    }





    /*@GetMapping("/admin/totalMembership/{date}")
    public ResponseEntity<Double> totalMembership(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<>(membershipTService.totalMembership(date), HttpStatus.OK);
    }*/

    /*@GetMapping("/admin/totalMembership/{dateFrom}/{dateTo}")
    public ResponseEntity<Double> totalMembership(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                              @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(membershipTService.totalMembership(dateFrom,dateTo), HttpStatus.OK);
    }*/

    /*@GetMapping("/admin/dateMembership/{dateFrom}/{dateTo}")
    public ResponseEntity<DateTransactions> dateMembership
            (@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
             @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        return new ResponseEntity<>(membershipTService.dateMembership(dateFrom, dateTo), HttpStatus.OK);

    }*/


}
