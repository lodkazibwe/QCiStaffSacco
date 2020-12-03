package com.qualitychemicals.qciss.transaction.converter;

import com.qualitychemicals.qciss.transaction.dto.LoanTDto;
import com.qualitychemicals.qciss.transaction.dto.TransactionDto;
import com.qualitychemicals.qciss.transaction.model.LoanT;
import com.qualitychemicals.qciss.transaction.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoanTConverter {
    @Autowired TransactionConverter transactionConverter;

    public LoanTDto entityToDto(LoanT loanT){
        LoanTDto loanTDto =new LoanTDto();
        loanTDto.setAcctFrom(loanT.getAcctFrom());
        loanTDto.setTransactionType(loanT.getTransactionType());
        loanTDto.setAmount(loanT.getAmount());
        loanTDto.setUserName(loanT.getUserName());
        loanTDto.setAcctTo(loanT.getAcctTo());
        loanTDto.setDate(loanT.getDate());
        loanTDto.setId(loanT.getId());
        loanTDto.setStatus(loanT.getStatus());
        loanTDto.setLoanId(loanT.getLoanId());
        return loanTDto;

    }
    public LoanT dtoToEntity(LoanTDto loanTDto){
        LoanT loanT =new LoanT();
        loanT.setAcctTo(loanTDto.getAcctTo());
        loanT.setAcctFrom(loanTDto.getAcctFrom());
        loanT.setAmount(loanTDto.getAmount());
        loanT.setDate(loanTDto.getDate());
        loanT.setStatus(loanTDto.getStatus());
        loanT.setTransactionType(loanTDto.getTransactionType());
        loanT.setUserName(loanTDto.getUserName());
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
