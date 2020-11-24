package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.dto.WorkDto;
import com.qualitychemicals.qciss.profile.converter.WorkConverter;
import com.qualitychemicals.qciss.profile.model.Work;
import com.qualitychemicals.qciss.profile.service.WorkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("profile/work")
public class WorkController {
    @Autowired WorkConverter workConverter;
    @Autowired
    WorkService workService;
    private final Logger logger= LoggerFactory.getLogger(WorkController.class);

    @PutMapping("/update")
    public ResponseEntity<WorkDto> addWorkDetail(@RequestBody WorkDto workDto){

        Work work=workService.updateWork(workDto);
        logger.info("success...");
        return new ResponseEntity<>(workConverter.entityToDto(work), HttpStatus.OK);

    }

    @PutMapping("/myPayrollSaving/{amount}")
    public ResponseEntity<WorkDto> updatePayrollSaving(@PathVariable double amount){

        Work work=workService.updatePayrollSaving(amount);
        logger.info("success...");
        return new ResponseEntity<>(workConverter.entityToDto(work), HttpStatus.OK);

    }

    @PutMapping("/myPayrollShares/{amount}")
    public ResponseEntity<WorkDto> updatePayrollShares(@PathVariable double amount){

        Work work=workService.updatePayrollShares(amount);
        logger.info("success...");
        return new ResponseEntity<>(workConverter.entityToDto(work), HttpStatus.OK);

    }

    @GetMapping("/admin/get/{workId}")
    public ResponseEntity<WorkDto> getWork(@PathVariable int workId){
        Work work =workService.getWork(workId);
        return new ResponseEntity<>(workConverter.entityToDto(work),HttpStatus.OK);

    }
    @GetMapping("admin/getByCompany/{companyName}")
    public ResponseEntity<List<WorkDto>> getByCompany(@PathVariable String companyName){
        List<Work> works=workService.getByCompany(companyName);
        return new ResponseEntity<>(workConverter.entityToDto(works), HttpStatus.OK);

    }

}
