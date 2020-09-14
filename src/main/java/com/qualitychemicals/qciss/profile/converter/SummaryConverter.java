package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.DTO.SummaryDTO;
import com.qualitychemicals.qciss.profile.model.Summary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SummaryConverter {
    public SummaryDTO entityToDto(Summary summary){
        SummaryDTO summaryDTO=new SummaryDTO();
        summaryDTO.setId(summary.getId());
        summaryDTO.setPendingFee(summary.getPendingFee());
        summaryDTO.setTotalSavings(summary.getTotalSavings());
        summaryDTO.setTotalShares(summary.getTotalShares());
        return summaryDTO;
    }

    public Summary dtoToEntity(SummaryDTO summaryDTO){
        Summary summary=new Summary();
        summary.setTotalShares(summaryDTO.getTotalShares());
        summary.setTotalSavings(summaryDTO.getTotalSavings());
        summary.setPendingFee(summaryDTO.getPendingFee());
        summary.setId(summaryDTO.getId());
        return summary;
    }
    public List<SummaryDTO> entityToDto(List<Summary> summaries){
        return summaries.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
