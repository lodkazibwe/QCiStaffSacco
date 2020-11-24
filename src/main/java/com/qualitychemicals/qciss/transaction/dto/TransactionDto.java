package com.qualitychemicals.qciss.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qualitychemicals.qciss.transaction.model.TransactionStatus;
import com.qualitychemicals.qciss.transaction.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date date;
    @NotNull(message ="amount cannot be Blank")
    @Min(value=5000, message="Amount should not be less than 5000")
    @Max(value=5000000, message="Amount should not be greater than 5m")
    private double amount;
    @NotNull
    private String acctFrom;
    @NotNull
    private String acctTo;
    @NotNull
    private String userName;
    private TransactionType transactionType;
    private TransactionStatus status;


}
