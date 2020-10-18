package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.model.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountConverter {
    public AccountDto entityToDto(Account account){
        AccountDto accountDTO =new AccountDto();
        accountDTO.setId(account.getId());
        accountDTO.setPendingFee(account.getPendingFee());
        accountDTO.setTotalSavings(account.getTotalSavings());
        accountDTO.setTotalShares(account.getTotalShares());
        return accountDTO;
    }

    public Account dtoToEntity(AccountDto accountDto){
        if(accountDto ==null){
            return new Account();
        }
        Account account =new Account();
        account.setTotalShares(accountDto.getTotalShares());
        account.setTotalSavings(accountDto.getTotalSavings());
        account.setPendingFee(accountDto.getPendingFee());
        account.setId(accountDto.getId());
        return account;
    }
    public List<AccountDto> entityToDto(List<Account> summaries){
        return summaries.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
