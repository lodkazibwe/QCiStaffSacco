package com.qualitychemicals.qciss.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareTDto extends TransactionDto{
    private double shareValue;
    private double shares;
}
