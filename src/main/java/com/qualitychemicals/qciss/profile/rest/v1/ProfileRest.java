package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.DTO.ProfileDTO;
import com.qualitychemicals.qciss.profile.converter.ProfileConverter;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileRest {
    @Autowired ProfileService profileService;
    @Autowired ProfileConverter profileConverter;

    @PostMapping("/add")
    public ResponseEntity<ProfileDTO> addProfile(@Valid @RequestBody ProfileDTO profileDTO){
        Profile profile=profileConverter.dtoToEntity(profileDTO);
        return new ResponseEntity<>(profileConverter.entityToDto
                (profileService.addProfile(profile)), HttpStatus.OK);
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
}
