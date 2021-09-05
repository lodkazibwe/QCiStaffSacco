package com.qualitychemicals.qciss.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", locale = "pt-BR", timezone = "EAT")
    private Date date;
    @NotNull(message = "amount cannot be Blank")
    private double amount;
    @NotNull(message = "acctFrom cannot be Blank")
    private String account;
    @NotNull(message = "narrative cannot be Blank")
    private String narrative;
    @NotNull(message = "userName cannot be Blank")
    private String userName;
    private String wallet;
    private TransactionStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING,  locale = "pt-BR", timezone = "EAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;
    private String transactionType;

}
