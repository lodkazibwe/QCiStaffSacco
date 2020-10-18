package com.qualitychemicals.qciss.transaction.converter;

import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.model.LoanT;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoanTConverter {

    public LoanTDto entityToDto(LoanT loanT){
        LoanTDto loanTDto =new LoanTDto();
        loanTDto.setLoanId(loanT.getLoanId());
        loanTDto.setUserName(loanT.getUserName());
        loanTDto.setAmount(loanT.getAmount());
        loanTDto.setDate(loanT.getDate());
        loanTDto.setId(loanT.getId());
        loanTDto.setStatus(loanT.getStatus());
        return loanTDto;

    }
    public LoanT dtoToEntity(LoanTDto loanTDto){
        LoanT loanT =new LoanT();
        loanT.setDate(loanTDto.getDate());
        loanTDto.setAmount(loanTDto.getAmount());
        loanT.setUserName(loanTDto.getUserName());
        loanT.setStatus(loanTDto.getStatus());
        loanT.setLoanId(loanTDto.getLoanId());
        return loanT;

    }
    public List<LoanTDto> entityToDto(List<LoanT> loanTS){
        return loanTS.stream().map(this::entityToDto).collect(Collectors.toList());

    }
    public List<LoanT> dtoToEntity(List<LoanTDto> loanTDtos){
        return loanTDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
