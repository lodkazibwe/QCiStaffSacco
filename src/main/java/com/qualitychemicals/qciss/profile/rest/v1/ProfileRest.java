package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.DTO.AccountDTO;
import com.qualitychemicals.qciss.profile.DTO.ProfileDTO;
import com.qualitychemicals.qciss.profile.DTO.SummaryDTO;
import com.qualitychemicals.qciss.profile.DTO.WorkDTO;
import com.qualitychemicals.qciss.profile.converter.AccountConverter;
import com.qualitychemicals.qciss.profile.converter.ProfileConverter;
import com.qualitychemicals.qciss.profile.converter.SummaryConverter;
import com.qualitychemicals.qciss.profile.converter.WorkConverter;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.Summary;
import com.qualitychemicals.qciss.profile.model.Work;
import com.qualitychemicals.qciss.profile.service.ProfileService;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/profile")
public class ProfileRest {
    @Autowired ProfileService profileService;
    @Autowired ProfileConverter profileConverter;
    @Autowired UserService userService;
    @Autowired AccountConverter accountConverter;
    @Autowired SummaryConverter summaryConverter;
    @Autowired WorkConverter workConverter;

    private final Logger logger= LoggerFactory.getLogger(ProfileRest.class);

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<ProfileDTO> createProfile(@Valid @RequestBody ProfileDTO profileDTO){
        logger.info("starting...");
        return new ResponseEntity<>(profileConverter.entityToDto(
                profileService.addProfile(profileDTO)), HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable int id){
        return new ResponseEntity<>(profileConverter.entityToDto
                (profileService.getProfile(id)), HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProfileDTO>> getAll(){
        return new ResponseEntity<>(profileConverter.entityToDto(profileService.getAll()),
                HttpStatus.OK);
    }
    @GetMapping("/account/get/{profileID}")
    public ResponseEntity<List<AccountDTO>> getUserAccounts(@PathVariable int profileID){
        List<Account> accounts=profileService.getAccounts(profileID);
        return new ResponseEntity<>(accountConverter.entityToDto(accounts), HttpStatus.OK);
    }
    @GetMapping("/summary/get/{profileID}")
    public ResponseEntity<SummaryDTO> getUserSummary(@PathVariable int profileID){
        Summary summary=profileService.getSummary(profileID);
        return new ResponseEntity<>(summaryConverter.entityToDto(summary), HttpStatus.OK);
    }

    @GetMapping("/workInfo/get/{profileID}")
    public ResponseEntity<WorkDTO> getWorkInfo(@PathVariable int profileID){
        Work work=profileService.getWorkInfo(profileID);
        return new ResponseEntity<>(workConverter.entityToDto(work), HttpStatus.OK);

    }

}
