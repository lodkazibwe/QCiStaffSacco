package com.qualitychemicals.qciss.firebase.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadDto {
    private String key;
    private String userName;
}
