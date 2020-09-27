package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.converter.SummaryConverter;
import com.qualitychemicals.qciss.profile.service.SummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/summary")
public class SummaryController {
    @Autowired SummaryConverter summaryConverter;
    @Autowired SummaryService summaryService;

    private final Logger logger= LoggerFactory.getLogger(SummaryController.class);


}
