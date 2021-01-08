package com.qualitychemicals.qciss.saccoData.appConfig;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppConfigReader {
    @Value("${root.email}")
    private String email;
    @Value("${root.username}")
    private String contact;
    @Value("${company.account}")
    private String saccoAccount;
}
