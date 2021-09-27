package com.qualitychemicals.qciss.account.converter;

import com.qualitychemicals.qciss.account.dto.WalletDto;
import com.qualitychemicals.qciss.account.model.Wallet;
import com.qualitychemicals.qciss.profile.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WalletConverter {
    @Autowired PersonService personService;
    public WalletDto entityToDto(Wallet wallet){
         WalletDto walletDto= new WalletDto();
        walletDto.setId(wallet.getId());
        walletDto.setUser(wallet.getUserName());
        walletDto.setContact(wallet.getContact());
        walletDto.setAmount(wallet.getAmount());
        walletDto.setLastTransaction(wallet.getLastTransaction());
        walletDto.setAccountRef(wallet.getAccountRef());
        walletDto.setName(wallet.getName());
        walletDto.setImageUrl(personService.bucket());
        return walletDto;

    }
    public Wallet dtoToEntity(WalletDto walletDto){
        Wallet wallet =new Wallet();
        wallet.setUserName(walletDto.getUser());
        wallet.setContact(walletDto.getContact());
        wallet.setAccountRef(walletDto.getAccountRef());
        wallet.setAmount(walletDto.getAmount());
        wallet.setName(walletDto.getName());
        wallet.setLastTransaction(walletDto.getLastTransaction());
        return wallet;
    }

    public List<WalletDto> entityToDto(List<Wallet> walletList){
        return  walletList.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Wallet> dtoToEntity(List<WalletDto> walletDtoList){
        return walletDtoList.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
