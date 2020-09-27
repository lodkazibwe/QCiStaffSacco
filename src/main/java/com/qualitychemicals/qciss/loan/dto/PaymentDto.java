package com.qualitychemicals.qciss.loan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    @ApiModelProperty(notes = "system Generated payment ID")
    private int id;
    @ApiModelProperty(notes = "system Generated Date of Payment (today)")
    private Date date;
    @NotBlank(message ="Amount cannot be Blank")
    @ApiModelProperty(notes = "Amount Paid")
    private double amount;
    @NotBlank(message ="Method cannot be Blank")
    @ApiModelProperty(notes = "Payment Method")
    private String method;//mobile,bank,direct,paypal
}
