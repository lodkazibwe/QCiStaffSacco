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
        TransactionDto transaction=transactionConverter.entityToDto(loanT);
        LoanTDto loanTDto =(LoanTDto)transaction;
        loanTDto.setLoanId(loanT.getLoanId());
        return loanTDto;

    }
    public LoanT dtoToEntity(LoanTDto loanTDto){
        Transaction transaction=transactionConverter.dtoToEntity(loanTDto);
        LoanT loanT =(LoanT)transaction;
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
