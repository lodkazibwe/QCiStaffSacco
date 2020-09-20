package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.DTO.AccountDTO;
import com.qualitychemicals.qciss.profile.model.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountConverter {
    public AccountDTO entityToDto(Account account){
        if(account==null){
            return null;
        }
        AccountDTO accountDTO=new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setCategory(account.getCategory());
        accountDTO.setAccountName(account.getAccountName());
        accountDTO.setAccountNumber(account.getAccountNumber());
        return accountDTO;
    }

    public Account dtoToEntity(AccountDTO accountDTO){
        if(accountDTO==null){
            return null;
        }
        Account account=new Account();
        account.setId(accountDTO.getId());
        account.setCategory(accountDTO.getCategory());
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setAccountName(accountDTO.getAccountName());
        return account;
    }
    public List<AccountDTO> entityToDto(List<Account> accounts){
        if(accounts==null){
            return null;
        }
        return accounts.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Account> dtoToEntity(List<AccountDTO> accountDTOs){
        if(accountDTOs==null){
            return null;
        }
        return accountDTOs.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
