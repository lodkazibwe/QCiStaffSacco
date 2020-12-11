package com.qualitychemicals.qciss.group.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class GroupDto {
    private int id;
    @NotNull
    private String name;
    private int accountId;
    List<MemberDto> members;
}
