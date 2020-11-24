package com.qualitychemicals.qciss.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotEmpty(message = "profile name must not be empty")
    @Size(min=3, message = "profile name at least three alphanumeric Characters")
    private String userName;
    @NotEmpty(message = "password must not be empty")
    @Size(min=6, message = "passKey must have at least 6 Characters")
    private String password;
}
