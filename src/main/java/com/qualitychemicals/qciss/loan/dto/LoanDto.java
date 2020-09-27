package com.qualitychemicals.qciss.loan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {
    @ApiModelProperty(notes = "db generated Loan ID")
    private int id;
    @NotBlank(message = "principal cannot be blank")
    @ApiModelProperty(notes = "Principal Amount")
    private double principal;
    @NotBlank(message = "duration cannot be blank")
    @ApiModelProperty(notes = "Loan duration (months)")
    private int duration;
    @NotBlank(message = "repayment cycle cannot be blank")
    @ApiModelProperty(notes = "Cycle of repayment (weakly,biWeakly,monthly),")
    private String repaymentCycle;
    @ApiModelProperty(notes = "generated date of loan application")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date applicationDate;
    @ApiModelProperty(notes = "generated loan release Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date releaseDate;
    @ApiModelProperty(notes = "how the cash is received (cash, mm,bank)")
    private String disbursedBy;
    @NotBlank(message = "missing first repayment date ")
    @ApiModelProperty(notes = "first Date of Repayment")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date firstRepaymentDate;
    @ApiModelProperty(notes = "Generated total interest")
    private double interest;
    @ApiModelProperty(notes = "Generated penalty amount")
    private double penalty;
    @ApiModelProperty(notes = "Generated loan status")
    private String status;
    @NotNull(message = "product cannot be null")
    @ApiModelProperty(notes = "loan product")
    private int productId;
    @ApiModelProperty(notes = "Generated admin userName/ID")
    private String approvedBy;
    @NotBlank(message = "borrower cannot be blank")
    @ApiModelProperty(notes = "borrower(userId)")
    private int  borrowerId;
    @ApiModelProperty(notes = "loan comment")
    private String comment;

}
