package com.qualitychemicals.qciss.transaction.rest.v1;

import com.qualitychemicals.qciss.transaction.dto.ShareTDto;
import com.qualitychemicals.qciss.transaction.dto.SharesTransactionsDto;
import com.qualitychemicals.qciss.transaction.service.ShareTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@RestController
@RequestMapping("transaction/shares")
public class ShareTController {
    @Autowired
    ShareTService shareTService;

    @PostMapping("/buy/{amount}")
    public ResponseEntity<ShareTDto> BuyMobile(@PathVariable double amount){
        return new ResponseEntity<>(shareTService.mobileShares(amount), HttpStatus.OK);
    }

    @GetMapping("/admin/shareTransactions")
    public ResponseEntity<SharesTransactionsDto> shareTransactions(){
        return new ResponseEntity<>(shareTService.shareTransactions(), HttpStatus.OK);

    }
}
