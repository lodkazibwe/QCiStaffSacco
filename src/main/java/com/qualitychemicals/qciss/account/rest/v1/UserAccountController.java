package com.qualitychemicals.qciss.account.rest.v1;

import com.qualitychemicals.qciss.account.converter.MembershipAccountConverter;
import com.qualitychemicals.qciss.account.converter.SavingsAccountConverter;
import com.qualitychemicals.qciss.account.converter.SharesAccountConverter;
import com.qualitychemicals.qciss.account.dto.MembershipAccountDto;
import com.qualitychemicals.qciss.account.dto.SavingsAccountDto;
import com.qualitychemicals.qciss.account.dto.SharesAccountDto;
import com.qualitychemicals.qciss.account.model.UserAccount;
import com.qualitychemicals.qciss.account.service.MembershipAccountService;
import com.qualitychemicals.qciss.account.service.SavingsAccountService;
import com.qualitychemicals.qciss.account.service.SharesAccountService;
import com.qualitychemicals.qciss.account.service.UserAccountService;
import com.qualitychemicals.qciss.saccoData.model.SaccoAccount;
import com.qualitychemicals.qciss.saccoData.service.SaccoAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/accounts")
public class UserAccountController {
   @Autowired
   UserAccountService userAccountService;
    @Autowired MembershipAccountService membershipAccountService;
    @Autowired MembershipAccountConverter membershipAccountConverter;
    @Autowired SavingsAccountConverter savingsAccountConverter;
    @Autowired SavingsAccountService savingsAccountService;
    @Autowired SharesAccountService sharesAccountService;
    @Autowired SharesAccountConverter sharesAccountConverter;
    @Autowired SaccoAccountService saccoAccountService;

    @GetMapping("/membership")
    public ResponseEntity<MembershipAccountDto> myMembershipAccount(){
        return  new ResponseEntity<>(membershipAccountConverter.entityToDto(membershipAccountService.getMyAccount()), HttpStatus.OK);

    }
    @GetMapping("/savings")
    public ResponseEntity<SavingsAccountDto> mySavingsAccount(){
        return  new ResponseEntity<>(savingsAccountConverter.entityToDto(savingsAccountService.getMyAccount()), HttpStatus.OK);

    }
    @GetMapping("/shares")
    public ResponseEntity<SharesAccountDto> mySharesAccount(){
        return  new ResponseEntity<>(sharesAccountConverter.entityToDto(sharesAccountService.getMyAccount()), HttpStatus.OK);

    }

    @GetMapping("/myAccounts")
    public ResponseEntity<List<UserAccount>> AllAccounts(){
        return  new ResponseEntity<>(userAccountService.getMyAll(), HttpStatus.OK);

    }

    /*@GetMapping("/get/{name}")
    public ResponseEntity<SaccoAccount> getSaccoAccount(@PathVariable String name){
        return  new ResponseEntity<>(saccoAccountService.getSaccoAccount(name), HttpStatus.OK);

    }*/

}
