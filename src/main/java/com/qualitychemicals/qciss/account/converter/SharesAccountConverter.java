package com.qualitychemicals.qciss.account.converter;

import com.qualitychemicals.qciss.account.dto.SharesAccountDto;
import com.qualitychemicals.qciss.account.model.SharesAccount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SharesAccountConverter {
    public SharesAccountDto entityToDto(SharesAccount sharesAccount){
        SharesAccountDto sharesAccountDto =new SharesAccountDto();
        sharesAccountDto.setId(sharesAccount.getId());
        sharesAccountDto.setAccountRef(sharesAccount.getAccountRef());
        sharesAccountDto.setAmount(sharesAccount.getAmount());
        sharesAccountDto.setDividend(sharesAccount.getDividend());
        sharesAccountDto.setLastTransaction(sharesAccount.getLastTransaction());
        sharesAccountDto.setRecordShares(sharesAccount.getRecordShares());
        sharesAccountDto.setShares(sharesAccount.getShares());
        sharesAccountDto.setName(sharesAccount.getName());
        sharesAccountDto.setUser(sharesAccount.getUserName());
        return sharesAccountDto;

    }
    public SharesAccount dtoToEntity(SharesAccountDto sharesAccountDto){
        SharesAccount sharesAccount =new SharesAccount();
        sharesAccount.setUserName(sharesAccountDto.getUser());
        sharesAccount.setDividend(sharesAccountDto.getDividend());
        sharesAccount.setRecordShares(sharesAccountDto.getRecordShares());
        sharesAccount.setShares(sharesAccountDto.getShares());
        sharesAccount.setAccountRef(sharesAccountDto.getAccountRef());
        sharesAccount.setAmount(sharesAccountDto.getAmount());
        sharesAccount.setName(sharesAccountDto.getName());
        sharesAccount.setLastTransaction(sharesAccountDto.getLastTransaction());
        return sharesAccount;
    }

    public List<SharesAccountDto> entityToDto(List<SharesAccount> sharesAccountList){
        return sharesAccountList.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<SharesAccount> dtoToEntity(List<SharesAccountDto> sharesAccountDtoList){
        return sharesAccountDtoList.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
