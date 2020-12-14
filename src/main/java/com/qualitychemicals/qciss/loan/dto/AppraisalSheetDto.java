package com.qualitychemicals.qciss.loan.dto;

import com.qualitychemicals.qciss.profile.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppraisalSheetDto {
private LoanDto loanRequest;
//private List<LoanDto> userLoans;
private List<DueLoanDto> outstandingLoans;
private UserDto userDto;

}
