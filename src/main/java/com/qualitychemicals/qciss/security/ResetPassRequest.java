package com.qualitychemicals.qciss.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPassRequest {
    @NotBlank(message="email cannot be blank")
    @Email(message="invalid Email")
    private String email;
    @NotNull(message = "mobile cannot be null")
    @Size(min=9, max=9, message = "invalid Contact length")
    private String contact;
}
