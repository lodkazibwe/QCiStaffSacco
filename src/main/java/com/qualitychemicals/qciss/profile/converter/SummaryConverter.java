package com.qualitychemicals.qciss.profile.converter;

import com.qualitychemicals.qciss.profile.dto.SummaryDto;
import com.qualitychemicals.qciss.profile.model.Summary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SummaryConverter {
    public SummaryDto entityToDto(Summary summary){
        SummaryDto summaryDTO=new SummaryDto();
        summaryDTO.setId(summary.getId());
        summaryDTO.setPendingFee(summary.getPendingFee());
        summaryDTO.setTotalSavings(summary.getTotalSavings());
        summaryDTO.setTotalShares(summary.getTotalShares());
        return summaryDTO;
    }

    public Summary dtoToEntity(SummaryDto summaryDto){
        if(summaryDto==null){
            return new Summary();
        }
        Summary summary=new Summary();
        summary.setTotalShares(summaryDto.getTotalShares());
        summary.setTotalSavings(summaryDto.getTotalSavings());
        summary.setPendingFee(summaryDto.getPendingFee());
        summary.setId(summaryDto.getId());
        return summary;
    }
    public List<SummaryDto> entityToDto(List<Summary> summaries){
        return summaries.stream().map(this::entityToDto).collect(Collectors.toList());
    }
}
