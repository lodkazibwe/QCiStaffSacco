package com.qualitychemicals.qciss.profile.service.impl;

import com.google.cloud.storage.Blob;
import com.google.firebase.cloud.StorageClient;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.dao.PersonDao;
import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.model.Person;
import com.qualitychemicals.qciss.profile.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonDao personDao;
    @Autowired
    private Environment environment;

    private final Logger logger= LoggerFactory.getLogger(PersonServiceImpl.class);
    @Override
    @Transactional
    public Person updatePerson(PersonDto personDto, int id) {
        logger.info("getting current details...");
        Person person=personDao.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No such person with ID: "+id));
        logger.info("updating...");
        person.setMobile(personDto.getMobile());
        person.setDob(personDto.getDob());
        person.setEmail(personDto.getEmail());
        person.setFirstName(personDto.getFirstName());
        person.setGender(personDto.getGender());
        person.setLastName(personDto.getLastName());
        person.setResidence(personDto.getResidence());
        person.setNin(personDto.getNin());
        return personDao.save(person);
    }

    @Override
    public Person getPerson(int id) {
        return personDao.findById(id).orElseThrow(()->new ResourceNotFoundException("No such info ID: "+id));
    }

    @Override
    public int userExists(String email, String mobile) {
        logger.info("checking user....");
        boolean bull1=personDao.existsByEmail(email);
        boolean bull2=personDao.existsByMobile(mobile);
        if(bull1){
            logger.info("email Exists....");
            return 1;}else if(bull2){
            logger.info("mobile Exists....");
            return 2;} else{
            logger.info("no user...");
            return 0;}

    }

    @Override
    public List<Person> getAll() {
        return personDao.findAll();
    }

    @Override
    public List<Person> getByGender(String gender) {
        return personDao.findByGender(gender);
    }

    @Override
    public void deletePerson(int id) {

    }

    @Override
    @Transactional
    public String updateImage(MultipartFile file, int id) throws IOException {
        logger.info("personalID= "+id);
        logger.info("uploadeing image...");
        File newFile=new File("C:\\Users\\joeko\\Desktop\\file\\"+file.getOriginalFilename());
        file.transferTo(newFile);
        logger.info("saving...");
        Person person = personDao.findById(id).
                orElseThrow(()->new ResourceNotFoundException("No such account with ID: "+id));
        person.setImage(newFile.getAbsolutePath());
        personDao.save(person);
        logger.info("saved successfully...");
        return newFile.getAbsolutePath();

    }

    public String uploadImage(MultipartFile myFile) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();

        InputStream file =  new BufferedInputStream(myFile.getInputStream());
        String name="profile/"+ myFile.getOriginalFilename();

        Blob blob=StorageClient.getInstance().bucket()
                .create(name, file);

        return name;

    }

    public String getImageUrl(String name) {

        return String.format(environment.getProperty("firebase.image-url"), name);
    }



}
