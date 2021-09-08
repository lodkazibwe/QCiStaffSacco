package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.profile.converter.NextOfKinConverter;
import com.qualitychemicals.qciss.profile.converter.PersonConverter;
import com.qualitychemicals.qciss.profile.dto.NextOfKinDto;
import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.model.NextOfKin;
import com.qualitychemicals.qciss.profile.model.Person;
import com.qualitychemicals.qciss.profile.service.PersonService;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URL;

//@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@CrossOrigin
@RestController
@RequestMapping("/profile/person")
public class PersonController {
    @Autowired
    PersonService personService;
    @Autowired PersonConverter personConverter;
    private final Logger logger= LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    @Autowired NextOfKinConverter nextOfKinConverter;

    @PostMapping("/uploadImage")
    public ResponseEntity<URL> updateImage(@RequestParam("file")MultipartFile file)
            //updateImage(@RequestParam("file") MultipartFile file)
            throws IOException {
        logger.info("processing image...");
        URL signedUrl= personService.uploadImage(file);
        return new ResponseEntity<>(signedUrl, HttpStatus.OK);
    }

    @GetMapping("/imageUrl")
    public ResponseEntity<URL> getImage(){
        return  new ResponseEntity<>(personService.downloadUrl(), HttpStatus.OK);

    }


    @GetMapping("/get/{personId}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable int personId){
        Person person=personService.getPerson(personId);
        return  new ResponseEntity<>(personConverter.entityToDto(person), HttpStatus.OK);

    }

    @PutMapping("/update")
    public ResponseEntity<PersonDto> updatePerson(@Valid @RequestBody PersonDto personDto){
        Person person =personService.updatePerson(personDto);
        return  new ResponseEntity<>(personConverter.entityToDto(person), HttpStatus.OK);
    }

    @PutMapping("/nextOfKin")
    public ResponseEntity<NextOfKinDto> addNextOfKin(@Valid @RequestBody NextOfKinDto nextOfKinDto){
        NextOfKin nextOfKin =userService.addNextOfKin(nextOfKinDto);
        return  new ResponseEntity<>(nextOfKinConverter.entityToDto(nextOfKin), HttpStatus.OK);
    }

}
