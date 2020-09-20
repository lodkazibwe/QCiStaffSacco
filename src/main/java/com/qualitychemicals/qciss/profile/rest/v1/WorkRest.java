package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.DTO.WorkDTO;
import com.qualitychemicals.qciss.profile.converter.WorkConverter;
import com.qualitychemicals.qciss.profile.model.Work;
import com.qualitychemicals.qciss.profile.service.WorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/work")
public class WorkRest {
    @Autowired WorkConverter workConverter;
    @Autowired WorkService workService;
    private final Logger logger= LoggerFactory.getLogger(ProfileRest.class);

    @PutMapping("/update/{id}")
    public ResponseEntity<WorkDTO> addWorkDetail(@RequestBody WorkDTO workDTO,@PathVariable int id){
        logger.info("converting details...");
        Work work=workConverter.dtoToEntity(workDTO);
        logger.info("saving details started...");
        Work savedDetail=workService.updateWork(work, id);
        logger.info("success...");
        return new ResponseEntity<>(workConverter.entityToDto(savedDetail), HttpStatus.OK);

    }
}
