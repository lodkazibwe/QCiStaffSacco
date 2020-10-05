package com.qualitychemicals.qciss.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class AuthResponse {
    private final String jwt;
}
