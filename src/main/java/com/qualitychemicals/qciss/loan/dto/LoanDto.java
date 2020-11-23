package com.qualitychemicals.qciss.loan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qualitychemicals.qciss.loan.model.*;
import com.qualitychemicals.qciss.transaction.model.TransactionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {
    @ApiModelProperty(notes = "db generated Loan ID")
    private int id;
    private String loanNumber;

    @NotNull(message = "principal cannot be blank")
    @ApiModelProperty(notes = "Principal Amount")
    private double principal;
    @NotNull(message = "duration cannot be blank")
    @ApiModelProperty(notes = "LoanT duration (months)")
    private int duration;
    @NotNull(message = "repayment cycle cannot be blank")
    @ApiModelProperty(notes = "Cycle of repayment (weakly,biWeakly,monthly),")
    private Cycle repaymentCycle;
    @NotNull(message = "repayment mode cannot be blank")
    @ApiModelProperty(notes = "mode of repayment (SALARY, SO, SYSTEM)")
    private RepaymentMode repaymentMode;
    @ApiModelProperty(notes = "generated date of loan application")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date applicationDate;
    @ApiModelProperty(notes = "generated loan release Date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date releaseDate;
    @NotNull(message = "disbursedBy cannot be blank")
    @ApiModelProperty(notes = "how the cash is received (cash, mobile,bank)")
    private TransactionType disbursedBy;
    @NotNull(message = "missing first repayment date ")
    @ApiModelProperty(notes = "first Date of Repayment")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date firstRepaymentDate;
    @ApiModelProperty(notes = "Generated total interest")
    private double interest;
    @ApiModelProperty(notes = "Generated Application Fee")
    private double handlingCharge;
    @ApiModelProperty(notes = "Generated penalty amount")
    private double penalty;
    @ApiModelProperty(notes = "Generated express Handling amount")
    private double expressHandling;
    @ApiModelProperty(notes = "Generated insurance Fee amount")
    private double insuranceFee;
    @NotNull(message = "transferCharge cannot be null")
    private double transferCharge;
    @ApiModelProperty(notes = "Generated earlyTopUpCharge amount")
    private double earlyTopUpCharge;
    @NotNull(message = "handlingMode cannot be null")
    private HandlingMode handlingMode;
    @NotNull(message = "topUpMode cannot be null")
    private HandlingMode topUpMode;
    @ApiModelProperty(notes = "Generated loan status")
    private LoanStatus status;
    @NotNull(message = "product cannot be null")
    @ApiModelProperty(notes = "loan product")
    private int productId;
    @ApiModelProperty(notes = "Generated value Total Paid")
    private double totalPaid;
    @ApiModelProperty(notes = "Generated value Total Due")
    private double totalDue;
    @ApiModelProperty(notes = "Generated admin userName/ID")
    private String preparedBy;
    @ApiModelProperty(notes = "Generated admin userName/ID")
    private String approvedBy;
    @ApiModelProperty(notes = "Generated borrower(userName/contact)")
    private String  borrower;

    @NotNull(message = "securityDto cannot be null")
    private SecurityDto securityDto;

    private int topUpLoanId;
    private double topUpLoanBalance;
    private String remarks;

}
