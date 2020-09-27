package com.qualitychemicals.qciss.loan.converter;

import com.qualitychemicals.qciss.loan.dto.RepaymentDto;
import com.qualitychemicals.qciss.loan.model.Repayment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RepaymentConverter {
    public RepaymentDto entityToDto(Repayment repayment){
        RepaymentDto repaymentDto=new RepaymentDto();
        repaymentDto.setId(repayment.getId());
        repaymentDto.setPaid(repayment.getPaid());
        repaymentDto.setStatus(repayment.getStatus());
        repaymentDto.setDate(repayment.getDate());
        repaymentDto.setAmount(repayment.getAmount());
        repaymentDto.setBalance(repayment.getBalance());
        return repaymentDto;
    }
    public Repayment dtoToEntity(RepaymentDto repaymentDto){
        Repayment repayment=new Repayment();
        repayment.setDate(repaymentDto.getDate());
        repayment.setStatus(repaymentDto.getStatus());
        repayment.setBalance(repaymentDto.getBalance());
        repayment.setAmount(repaymentDto.getAmount());
        repayment.setPaid(repaymentDto.getPaid());
        //repayment.setId(repaymentDto.getId());
        return repayment;
    }
    public List<RepaymentDto> entityToDto(List<Repayment> repayments){
        return repayments.stream().map(this::entityToDto).collect(Collectors.toList());
    }
    public List<Repayment> dtoToEntity(List<RepaymentDto> repaymentDtos){
        return repaymentDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
