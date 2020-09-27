package com.qualitychemicals.qciss.loan.converter;

import com.qualitychemicals.qciss.loan.dto.PaymentDto;
import com.qualitychemicals.qciss.loan.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentConverter {
   public PaymentDto entityToDto(Payment payment){

        PaymentDto paymentDto=new PaymentDto();
        paymentDto.setDate(payment.getDate());
        paymentDto.setAmount(payment.getAmount());
        paymentDto.setMethod(payment.getMethod());
        return paymentDto;
    }

    public Payment dtoToEntity(PaymentDto paymentDto){
        Payment payment=new Payment();
        payment.setAmount(paymentDto.getAmount());
        payment.setMethod(paymentDto.getMethod());
        payment.setDate(paymentDto.getDate());
        return payment;

    }
   public List<PaymentDto> entityToDto(List<Payment> payments){
       return payments.stream().map(this::entityToDto).collect(Collectors.toList());
   }

   public List<Payment> dtoToEntity(List<PaymentDto> paymentDtos){
       return paymentDtos.stream().map(this::dtoToEntity).collect(Collectors.toList()) ;
   }

}
