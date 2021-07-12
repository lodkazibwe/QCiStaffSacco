package com.qualitychemicals.qciss.loan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityDto {
    private int id;
    @NotNull(message = "description cannot be null")
    private String description;
    @NotNull(message = "guarantor cannot be null")
    private String guarantor;
    private String file;
}
