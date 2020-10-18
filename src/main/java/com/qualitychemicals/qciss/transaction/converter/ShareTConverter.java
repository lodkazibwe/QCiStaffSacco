package com.qualitychemicals.qciss.transaction.converter;

import com.qualitychemicals.qciss.transaction.dto.ShareTDto;
import com.qualitychemicals.qciss.transaction.model.ShareT;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ShareTConverter {
    //@Autowired TransactionConverter transactionConverter;
    public ShareTDto entityToDto(ShareT shareT){
        //TransactionDto transaction=transactionConverter.entityToDto(shareT);
        //ShareTDto shareTDto =(ShareTDto)transaction;
        ShareTDto shareTDto=new ShareTDto();

        shareTDto.setDate(shareT.getDate());
        shareTDto.setShares(shareT.getShares());
        shareTDto.setUnitCost(shareT.getUnitCost());
        shareTDto.setAmount(shareT.getAmount());
        shareTDto.setStatus(shareT.getStatus());
        shareTDto.setUserName(shareT.getUserName());
        shareTDto.setId(shareT.getId());
        return shareTDto;

    }
    public ShareT dtoToEntity(ShareTDto shareTDto){
        ShareT shareT =new ShareT();
        shareT.setDate(shareTDto.getDate());
        shareT.setAmount(shareTDto.getAmount());
        shareT.setShares(shareTDto.getShares());
        shareT.setUnitCost(shareTDto.getUnitCost());
        shareT.setStatus(shareTDto.getStatus());
        shareT.setUserName(shareTDto.getUserName());
        return shareT;
    }

    public List<ShareTDto> entityToDto(List<ShareT> shareTS){
        return shareTS.stream().map(this::entityToDto).collect(Collectors.toList());

    }
    public List<ShareT> dtoToEntity(List<ShareTDto> shareTDtos){
        return shareTDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());

    }
}
