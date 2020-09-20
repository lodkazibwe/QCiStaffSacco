package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.DTO.AccountDTO;
import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountRest {
    @Autowired AccountService accountService;
    @Autowired AccountConverter accountConverter;

    @PostMapping("/add/{profileId}")
    public ResponseEntity<List<AccountDTO>> addAccount(@RequestBody AccountDTO accountDTO, @PathVariable int profileId){

        return new ResponseEntity<>(accountConverter.entityToDto(
                accountService.addAccount(accountDTO, profileId)),HttpStatus.OK);

    }
    @GetMapping("/get/{accountId}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable int accountId){
        Account account=accountService.getAccount(accountId);
        return new ResponseEntity<>(accountConverter.entityToDto(account), HttpStatus.OK);

    }
    @GetMapping("/getByCat/{category}")
    public ResponseEntity<List<AccountDTO>> getByCategory(@PathVariable String category){
        List<Account> accounts=accountService.getByCategory(category);
        return new ResponseEntity<>(accountConverter.entityToDto(accounts), HttpStatus.OK);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable int accountId){
        Account account=accountService.updateAccount(accountDTO, accountId);
        return new ResponseEntity<>(accountConverter.entityToDto(account), HttpStatus.OK);
    }

}
