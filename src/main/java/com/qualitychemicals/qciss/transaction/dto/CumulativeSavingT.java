package com.qualitychemicals.qciss.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CumulativeSavingT {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", locale = "pt-BR", timezone = "EAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDateTime;
    private double amount;
    private String account;
    private String narrative;
    private double cumulativeAmount;
}
