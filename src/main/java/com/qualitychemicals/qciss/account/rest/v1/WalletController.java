package com.qualitychemicals.qciss.account.rest.v1;

import com.qualitychemicals.qciss.account.converter.WalletConverter;
import com.qualitychemicals.qciss.account.dto.WalletDto;
import com.qualitychemicals.qciss.account.model.Wallet;
import com.qualitychemicals.qciss.account.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired WalletService walletService;
    @Autowired
    WalletConverter walletConverter;

     @GetMapping("/get/{accountRef}")
    public ResponseEntity<WalletDto> getWallet(@PathVariable String accountRef){
              return  new ResponseEntity<>(walletConverter.entityToDto(walletService.getWallet(accountRef)), HttpStatus.OK);

         }

    @PostMapping("/initiate/{amount}")
    public ResponseEntity<String> initiateDeposit(@PathVariable double amount) {
        return new ResponseEntity<>(walletService.deposit(amount), HttpStatus.OK);
    }

    @PutMapping("/refresh")
    public ResponseEntity<Wallet> refreshAccount() {
        return new ResponseEntity<>(walletService.refresh(), HttpStatus.OK);
    }

    @GetMapping("/myWallet")
        public ResponseEntity<WalletDto> myWallet(){
            return  new ResponseEntity<>(walletConverter.entityToDto(walletService.getMyWallet()), HttpStatus.OK);

        }



    }
