package com.qualitychemicals.qciss.loan.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @ApiModelProperty(notes = "db generated LoanT Product ID")
    private  int id;
    @NotBlank(message = "name cannot be blank")
    @Size(min=3, message = "name at least 3 Characters")
    @ApiModelProperty(notes = "LoanT Product Name")
    private String name;
    @NotBlank(message = "product Number cannot be blank")
    @Size(min=4, max=4, message = "product Number must have 4 Characters")
    private String productNumber;
    @NotNull(message ="penalty cannot be Blank")
    @ApiModelProperty(notes = "Loan Product penalty (%age of principal)")
    private double penalty;
    @NotNull(message ="interest cannot be Blank")
    @ApiModelProperty(notes = "Loan Product Interest rate (%age of principal)")
    private double interest;//%age of principal
    @NotNull(message ="handling Charge cannot be Blank")
    private double handlingCharge;
    @NotNull(message ="express Handling Charge cannot be Blank")
    private double expressHandling;
    @NotNull(message ="early Top Up Charge cannot be Blank")
    private double earlyTopUp;
    @NotNull(message ="maximum cannot be Blank")
    private double maximum;
    @NotNull(message ="insuranceFee cannot be Blank")
    private double insuranceRate;
    @NotNull(message ="Min Amount cannot be Blank")
    @ApiModelProperty(notes = "Loan Product min request amount")
    private double minAmount;
    @NotNull(message ="Max Amount cannot be Blank")
    @ApiModelProperty(notes = "Loan Product max request amount")
    private double maxAmount;
    @NotNull(message ="Min Duration cannot be Blank")
    @ApiModelProperty(notes = "Loan Product min payment Duration")
    private int minDuration;//months
    @NotNull(message ="Max Duration cannot be Blank")
    @ApiModelProperty(notes = "Loan Product max payment Duration")
    private int maxDuration;//months
    @NotBlank(message ="Provide a Loan Description")
    @ApiModelProperty(notes = "Loan Product Description")
    private String description;
}
