package com.qualitychemicals.qciss.saccoData.converter;

import com.qualitychemicals.qciss.saccoData.dto.ScheduleLoanDto;
import com.qualitychemicals.qciss.saccoData.model.ScheduleLoan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleLoanConverter {
    public ScheduleLoanDto entityToDto(ScheduleLoan scheduleLoan){
        ScheduleLoanDto scheduleLoanDto= new ScheduleLoanDto();
        /*if(scheduleLoan==null){
            return scheduleLoanDto;
        }*/

        scheduleLoanDto.setDue(scheduleLoan.getDue());
        scheduleLoanDto.setBorrower(scheduleLoan.getBorrower());
        scheduleLoanDto.setInterest(scheduleLoan.getInterest());
        scheduleLoanDto.setLastDueDate(scheduleLoan.getLastDueDate());
        scheduleLoanDto.setLoanId(scheduleLoan.getLoanId());
        scheduleLoanDto.setNextDue(scheduleLoan.getNextDue());
        scheduleLoanDto.setNextDueDate(scheduleLoan.getNextDueDate());
        scheduleLoanDto.setPenalty(scheduleLoan.getPenalty());
        scheduleLoanDto.setPrincipal(scheduleLoan.getPrincipal());
        scheduleLoanDto.setProduct(scheduleLoan.getProduct());
        scheduleLoanDto.setRepaymentMode(scheduleLoan.getRepaymentMode());
        scheduleLoanDto.setTotalBalance(scheduleLoan.getTotalBalance());
        scheduleLoanDto.setTotalPaid(scheduleLoan.getTotalPaid());
        return scheduleLoanDto;
    }

    public ScheduleLoan dtoToEntity(ScheduleLoanDto scheduleLoanDto){
        ScheduleLoan scheduleLoan= new ScheduleLoan();
        /*if(scheduleLoanDto==null){
            return scheduleLoan;
        }*/

        scheduleLoan.setDue(scheduleLoanDto.getDue());
        scheduleLoan.setLoanId(scheduleLoanDto.getLoanId());
        scheduleLoan.setBorrower(scheduleLoanDto.getBorrower());
        scheduleLoan.setInterest(scheduleLoanDto.getInterest());
        scheduleLoan.setLastDueDate(scheduleLoanDto.getLastDueDate());
        scheduleLoan.setNextDue(scheduleLoanDto.getNextDue());
        scheduleLoan.setNextDueDate(scheduleLoanDto.getNextDueDate());
        scheduleLoan.setPenalty(scheduleLoanDto.getPenalty());
        scheduleLoan.setTotalPaid(scheduleLoanDto.getTotalPaid());
        scheduleLoan.setPrincipal(scheduleLoanDto.getPrincipal());
        scheduleLoan.setProduct(scheduleLoanDto.getProduct());
        scheduleLoan.setRepaymentMode(scheduleLoanDto.getRepaymentMode());
        scheduleLoan.setTotalBalance(scheduleLoanDto.getTotalBalance());
        return scheduleLoan;
    }

    public List<ScheduleLoanDto> entityToDto(List<ScheduleLoan> scheduleLoans){
        /*if(scheduleLoans==null){
            ScheduleLoan scheduleLoan= new ScheduleLoan();
            scheduleLoans.add(scheduleLoan);
        }*/
        return scheduleLoans.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<ScheduleLoan> dtoToEntity(List<ScheduleLoanDto> scheduleLoans){
        /*if(scheduleLoans==null){
            ScheduleLoanDto scheduleLoan= new ScheduleLoanDto();
            scheduleLoans.add(scheduleLoan);
        }*/
        return scheduleLoans.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
