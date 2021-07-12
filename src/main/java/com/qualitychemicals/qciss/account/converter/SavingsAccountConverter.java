package com.qualitychemicals.qciss.account.converter;

import com.qualitychemicals.qciss.account.dto.SavingsAccountDto;
import com.qualitychemicals.qciss.account.model.SavingsAccount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SavingsAccountConverter {
    public SavingsAccountDto entityToDto(SavingsAccount savingsAccount){
        SavingsAccountDto savingsAccountDto =new SavingsAccountDto();
        savingsAccountDto.setId(savingsAccount.getId());
        savingsAccountDto.setAccountRef(savingsAccount.getAccountRef());
        savingsAccountDto.setAmount(savingsAccount.getAmount());
        savingsAccountDto.setInterest(savingsAccount.getInterest());
        savingsAccountDto.setLastTransaction(savingsAccount.getLastTransaction());
        savingsAccountDto.setName(savingsAccount.getName());
        savingsAccountDto.setUser(savingsAccount.getUserName());
        return savingsAccountDto;

    }

    public SavingsAccount dtoToEntity(SavingsAccountDto savingsAccountDto){
        SavingsAccount savingsAccount =new SavingsAccount();
        savingsAccount.setUserName(savingsAccountDto.getUser());
        savingsAccount.setName(savingsAccountDto.getName());
        savingsAccount.setInterest(savingsAccountDto.getInterest());
        savingsAccount.setAccountRef(savingsAccountDto.getAccountRef());
        savingsAccount.setAmount(savingsAccountDto.getAmount());
        savingsAccount.setLastTransaction(savingsAccountDto.getLastTransaction());
        return savingsAccount;

    }

    public List<SavingsAccountDto> entityToDto(List<SavingsAccount> savingsAccountList){
        return savingsAccountList.stream().map(this::entityToDto).collect(Collectors.toList());

    }
    public List<SavingsAccount> dtoToEntity(List<SavingsAccountDto> savingsAccountDtoList){
        return savingsAccountDtoList.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
