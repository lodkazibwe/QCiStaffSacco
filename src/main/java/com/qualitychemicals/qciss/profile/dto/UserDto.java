package com.qualitychemicals.qciss.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    @NotNull(message = "invalid  user details...")
    @Valid
    private PersonDto personDto;
    @NotNull(message = "invalid  account details...")
    @Valid
    private AccountDto accountDto;
    @NotNull(message = "invalid  work details...")
    @Valid
    private WorkDto workDto;

}
