package com.qualitychemicals.qciss.profile.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private int id;
    private String category;
    private String accountName;
    private String accountNumber;
}
