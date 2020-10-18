package com.qualitychemicals.qciss.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneTimeLoginDto {
    @NotNull(message = "mobile cannot be null")
    @Size(min=10, max=10, message = "invalid Contact length")
    @Pattern(regexp="(^[0-9]{10})", message = "invalid Contact")
    private String mobile;

    @NotEmpty(message = "password must not be empty")
    @Size(min=4, max=4, message = "passKey must have 4 Characters")
    private String pin;
}
