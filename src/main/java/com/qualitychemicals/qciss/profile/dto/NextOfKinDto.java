package com.qualitychemicals.qciss.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NextOfKinDto {
    @NotBlank(message = "name name cannot be blank")
    private String name;
    @Size(min=9, max=9, message = "invalid Contact length")
    @Pattern(regexp="(^[0-9]{9})", message = "invalid Contact")
    private String contact;
    @NotBlank(message = "relationship name cannot be blank")
    private String relationship;
}
