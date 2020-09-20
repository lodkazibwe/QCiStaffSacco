package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.service.PersonalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/personalInfo")
public class PersonalRest {
    @Autowired PersonalService personalService;
    private final Logger logger= LoggerFactory.getLogger(ProfileRest.class);

    @PostMapping("upload/image/{infoId}")
    public ResponseEntity<String> updateImage(@RequestParam("file")MultipartFile file, @PathVariable int infoId)
            throws IOException {
        logger.info("processing image...");
        String s=personalService.updateImage(file, infoId);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
