package com.qualitychemicals.qciss.profile.rest.v1;

import com.qualitychemicals.qciss.firebase.message.ChatService;
import com.qualitychemicals.qciss.profile.converter.PersonConverter;
import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.model.Person;
import com.qualitychemicals.qciss.profile.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = {"https://qcstaffsacco.com", "https://qcstaffsacco.com/admin"}, allowedHeaders = "*")
@RestController
@RequestMapping("/profile/person")
public class PersonController {
    @Autowired
    PersonService personService;
    @Autowired PersonConverter personConverter;
    private final Logger logger= LoggerFactory.getLogger(UserController.class);

    @PostMapping("/uploadImage}")
    public ResponseEntity<String> updateImage(@RequestParam("file")MultipartFile file)
            throws IOException {
        logger.info("processing image...");
        String s= personService.uploadImage(file);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }


    @GetMapping("/get/{personId}")
    public ResponseEntity<PersonDto> getPerson(@PathVariable int personId){
        Person person=personService.getPerson(personId);
        return  new ResponseEntity<>(personConverter.entityToDto(person), HttpStatus.OK);

    }





}
