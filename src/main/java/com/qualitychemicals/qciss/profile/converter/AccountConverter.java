package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.dto.AccountDto;
import com.qualitychemicals.qciss.profile.model.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountConverter {
    public AccountDto entityToDto(Account account){
        if(account==null){
            return null;
        }
        AccountDto accountDTO=new AccountDto();
        accountDTO.setId(account.getId());
        accountDTO.setCategory(account.getCategory());
        accountDTO.setAccountName(account.getAccountName());
        accountDTO.setAccountNumber(account.getAccountNumber());
        return accountDTO;
    }

    public Account dtoToEntity(AccountDto accountDto){
        if(accountDto==null){
            return null;
        }
        Account account=new Account();
        account.setId(accountDto.getId());
        account.setCategory(accountDto.getCategory());
        account.setAccountNumber(accountDto.getAccountNumber());
        account.setAccountName(accountDto.getAccountName());
        return account;
    }
    public List<AccountDto> entityToDto(List<Account> accounts){
        if(accounts==null){
            return null;
        }
        return accounts.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Account> dtoToEntity(List<AccountDto> accountDtos){
        if(accountDtos ==null){
            return null;
        }
        return accountDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
