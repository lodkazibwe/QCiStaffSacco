package com.qualitychemicals.qciss.firebase.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    @NotNull(message="text can not be null")
    private String text;
}
