package com.qualitychemicals.qciss.profile.dto;

import com.qualitychemicals.qciss.profile.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDto {
    private int userId;
    private String userName;
    private Status status;
    @NotNull(message = "invalid  profile details...")
    @Valid
    private PersonDto personDto;
    @NotNull(message = "invalid  account details...")
    @Valid
    private AccountDto accountDto;
    @NotNull(message = "invalid  work details...")
    @Valid
    private WorkDto workDto;
    private BankDto bankDto;
    private NextOfKinDto nextOfKinDto;


}
