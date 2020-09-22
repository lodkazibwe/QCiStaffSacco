package com.qualitychemicals.qciss.profile.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProfileDTO {
    private int id;
    @NotNull
    @Valid
    private UserDTO userDetail;
    @NotNull(message = "invalid  details...")
    @Valid
    private PersonalDTO personalInfo;

}
