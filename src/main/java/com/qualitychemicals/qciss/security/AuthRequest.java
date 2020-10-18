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
    @NotEmpty(message = "user name must not be empty")
    @Size(min=3, message = "user name at least three alphanumeric Characters")
    @Pattern(regexp="^[a-zA-Z0-9]+$", message = "Invalid User name")
    private String userName;
    @NotEmpty(message = "password must not be empty")
    @Size(min=6, message = "passKey must have at least 6 Characters")
    private String password;
}
