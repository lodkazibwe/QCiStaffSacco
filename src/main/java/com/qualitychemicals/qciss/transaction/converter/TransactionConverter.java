package com.qualitychemicals.qciss.transaction.converter;

import com.qualitychemicals.qciss.transaction.dto.TransactionDto;
import com.qualitychemicals.qciss.transaction.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionConverter {
    public TransactionDto entityToDto(Transaction transaction){
        TransactionDto transactionDto=new TransactionDto();
        transactionDto.setAmount(transaction.getAmount());
        transactionDto.setDate(transaction.getDate());
        transactionDto.setId(transaction.getId());
        transactionDto.setStatus(transaction.getStatus());
        transactionDto.setUserName(transaction.getUserName());
        transactionDto.setTransactionType(transaction.getTransactionType());
        return transactionDto;

    }
    public Transaction dtoToEntity(TransactionDto transactionDto){
        Transaction transaction=new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setDate(transactionDto.getDate());
        transaction.setStatus(transactionDto.getStatus());
        transaction.setUserName(transactionDto.getUserName());
        transaction.setTransactionType(transactionDto.getTransactionType());
        return transaction;

    }
    public List<TransactionDto> entityToDto(List<Transaction> transactions){
        return transactions.stream().map(this::entityToDto).collect(Collectors.toList());

    }
    public List<Transaction> dtoToEntity(List<TransactionDto> transactions){
        return transactions.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
