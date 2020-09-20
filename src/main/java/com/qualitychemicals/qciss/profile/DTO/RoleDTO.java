package com.qualitychemicals.qciss.profile.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    @Id
    @GeneratedValue
    private int id;
    @NotNull
    private String role;
}
