package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.model.Transaction;
import com.qualitychemicals.qciss.transaction.service.MembershipTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/transaction/membership")
public class MembershipTController {
    @Autowired MembershipTService membershipTService;

    @PutMapping("/pay/{amount}")
    public ResponseEntity<Transaction> payMembership(@PathVariable double amount){
        return new ResponseEntity<>(membershipTService.payMembership(amount), HttpStatus.OK);
    }
}
