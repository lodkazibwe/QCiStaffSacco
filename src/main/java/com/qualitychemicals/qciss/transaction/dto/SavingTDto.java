package com.qualitychemicals.qciss.transaction.dto;

import com.qualitychemicals.qciss.transaction.model.SavingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingTDto extends TransactionDto{
    private SavingType savingType;
}
