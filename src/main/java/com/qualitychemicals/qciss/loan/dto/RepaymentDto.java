package com.qualitychemicals.qciss.loan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepaymentDto {
    @ApiModelProperty(notes = "Generated RepaymentDao ID")
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @ApiModelProperty(notes = "Generated Date of RepaymentDao")
    private Date date;
    @ApiModelProperty(notes = "Generated RepaymentDao Amount")
    private double amount;
    @ApiModelProperty(notes = "Generated part of RepaymentDao Amount Paid")
    private double paid;
    @ApiModelProperty(notes = "Generated part of RepaymentDao Amount not Paid")
    private double balance;
    @ApiModelProperty(notes = "Generated RepaymentDao TransactionStatus")
    private String status;
}
