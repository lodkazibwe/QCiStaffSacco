package com.qualitychemicals.qciss.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResp {
    private final String jwt;
    private String name;
    private String role;
}
