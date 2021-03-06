package com.qualitychemicals.qciss.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanPayDto {
    @NotNull
    private int loanId;
    //@NotNull
   // private String loanRef;
    @NotNull
    private double amount;

}
