package com.qualitychemicals.qciss.saccoData.rest.v1;

import com.qualitychemicals.qciss.saccoData.converter.DeductionScheduleConverter;
import com.qualitychemicals.qciss.saccoData.dto.DeductionScheduleDTO;
import com.qualitychemicals.qciss.saccoData.service.DeductionScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RestController
//@RequestMapping("/schedule")
@Component
public class DeductionScheduleController {
    @Autowired DeductionScheduleConverter deductionScheduleConverter;
    @Autowired DeductionScheduleService deductionScheduleService;


    /*@PostMapping("/root/save/{company}")
    public ResponseEntity<List<DeductionScheduleDTO>> save(@RequestBody List<DeductionScheduleDTO> deductionScheduleDTOs, @PathVariable String company){
        return new ResponseEntity<>(deductionScheduleConverter.entityToDto(deductionScheduleService
                .save(deductionScheduleDTOs,company,new Date())), HttpStatus.OK);
    }*/

    @GetMapping("/root/get/{company}/{date}")
    public ResponseEntity<List<DeductionScheduleDTO>> get(
            @PathVariable String company, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return new ResponseEntity<>(deductionScheduleConverter.entityToDto(deductionScheduleService
                .get(company,date)), HttpStatus.OK);
    }

    //generateDeductionSchedule every month auto=scheduled

}
