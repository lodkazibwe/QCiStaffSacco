package com.qualitychemicals.qciss.loan.converter;
import com.qualitychemicals.qciss.loan.dto.SecurityDto;
import com.qualitychemicals.qciss.loan.model.Security;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityConverter {
    public SecurityDto entityToDto(Security security) {
        SecurityDto securityDto=new SecurityDto();
        securityDto.setDescription(security.getDescription());
        securityDto.setFile(security.getFile());
        securityDto.setGuarantor(security.getGuarantor());
        securityDto.setId(security.getId());
        return securityDto;

    }

    public Security dtoToEntity(SecurityDto securityDto) {
        Security security=new Security();
        security.setDescription(securityDto.getDescription());
        security.setFile(securityDto.getFile());
        security.setGuarantor(securityDto.getGuarantor());
        return security;

    }

    public List<SecurityDto> entityToDto(List<Security> securities){
        return securities.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Security> dtoToEntity(List<SecurityDto> loanDtos){
        return loanDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }
}
