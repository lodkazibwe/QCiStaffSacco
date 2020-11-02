package com.qualitychemicals.qciss.transaction.dto;

import com.qualitychemicals.qciss.transaction.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanReleaseDto {
    @NotNull
    private int loanId;
    @NotNull
    private TransactionType transactionType;

}
