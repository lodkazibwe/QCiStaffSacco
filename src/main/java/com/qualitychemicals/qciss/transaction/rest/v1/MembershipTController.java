package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.dto.MembershipTDto;
import com.qualitychemicals.qciss.transaction.service.MembershipTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@RestController
@RequestMapping("/transaction/membership")
public class MembershipTController {
    @Autowired
    MembershipTService membershipTService;

    @PutMapping("/pay/{amount}")
    public ResponseEntity<MembershipTDto> payMembership(@PathVariable double amount){
        return new ResponseEntity<>(membershipTService.payMembership(amount), HttpStatus.OK);
    }
}
