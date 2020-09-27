package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.dto.AccountDto;
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
public class AccountController {
    @Autowired AccountService accountService;
    @Autowired AccountConverter accountConverter;

    @PostMapping("/add/{userId}")
    public ResponseEntity<List<AccountDto>> addAccount(@RequestBody AccountDto accountDTO, @PathVariable int userId){

        return new ResponseEntity<>(accountConverter.entityToDto(
                accountService.addAccount(accountDTO, userId)),HttpStatus.OK);

    }
    @GetMapping("/get/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable int accountId){
        Account account=accountService.getAccount(accountId);
        return new ResponseEntity<>(accountConverter.entityToDto(account), HttpStatus.OK);

    }
    @GetMapping("/getByCat/{category}")
    public ResponseEntity<List<AccountDto>> getByCategory(@PathVariable String category){
        List<Account> accounts=accountService.getByCategory(category);
        return new ResponseEntity<>(accountConverter.entityToDto(accounts), HttpStatus.OK);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<AccountDto> updateAccount(@RequestBody AccountDto accountDTO, @PathVariable int accountId){
        Account account=accountService.updateAccount(accountDTO, accountId);
        return new ResponseEntity<>(accountConverter.entityToDto(account), HttpStatus.OK);
    }

}
