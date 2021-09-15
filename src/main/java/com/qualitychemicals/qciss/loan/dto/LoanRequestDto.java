package com.qualitychemicals.qciss.loan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qualitychemicals.qciss.loan.model.Cycle;
import com.qualitychemicals.qciss.loan.model.HandlingMode;
import com.qualitychemicals.qciss.loan.model.RepaymentMode;
import com.qualitychemicals.qciss.transaction.dto.TransactionType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class LoanRequestDto {
    @NotNull(message = "product cannot be null")
    @ApiModelProperty(notes = "loan product")
    private int productId;
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
    @ApiModelProperty(notes = "mode of repayment (SALARY, WALLET, SYSTEM)")
    private RepaymentMode repaymentMode;
    //@NotNull(message = "disbursedBy cannot be blank")
    //@ApiModelProperty(notes = "how the cash is received (cash, mobile,bank)")
    private TransactionType disbursedBy;
    @NotNull(message = "missing first repayment date ")
    @ApiModelProperty(notes = "first Date of Repayment")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date firstRepaymentDate;
    @NotNull(message = "handlingMode cannot be null")
    private HandlingMode handlingMode;
    @NotNull(message = "topUpMode cannot be null")
    private HandlingMode topUpMode;
    //@Valid
    //@NotNull(message = "securityDto cannot be null")
    private SecurityDto securityDto;

}
