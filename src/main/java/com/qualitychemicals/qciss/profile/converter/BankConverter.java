package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.dto.BankDto;
import com.qualitychemicals.qciss.profile.model.Bank;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankConverter {
    public BankDto entityToDto(Bank bank){
        if(bank==null){
            return new BankDto();
        }
        BankDto bankDto=new BankDto();
        bankDto.setAccountName(bank.getAccountName());
        bankDto.setAccountNumber(bank.getAccountNumber());
        bankDto.setBranch(bank.getBranch());
        bankDto.setId(bank.getId());
        bankDto.setName(bank.getName());
        return bankDto;

    }
    public Bank dtoToEntity(BankDto bankDto){
        Bank bank=new Bank();
        bank.setAccountName(bankDto.getAccountName());
        bank.setAccountNumber(bankDto.getAccountNumber());
        bank.setBranch(bankDto.getBranch());
        bank.setName(bankDto.getName());
        return  bank;

    }
    public List<BankDto> entityToDto(List<Bank> banks){
        return banks.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Bank> dtoToEntity(List<BankDto> banks){
        return banks.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
