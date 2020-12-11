package com.qualitychemicals.qciss.group.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MemberDto {
    @NotNull
    private int userId;
    @NotNull
    private double saving;
    @NotNull
    private Position position;

}
