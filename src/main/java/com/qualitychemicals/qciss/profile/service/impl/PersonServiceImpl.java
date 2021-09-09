package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.firebase.file.FileService;
import com.qualitychemicals.qciss.profile.dao.PersonDao;
import com.qualitychemicals.qciss.profile.dao.UserDao;
import com.qualitychemicals.qciss.profile.dao.UserFileDao;
import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.model.Person;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.model.Status;
import com.qualitychemicals.qciss.profile.model.UserFile;
import com.qualitychemicals.qciss.profile.service.PersonService;
import com.qualitychemicals.qciss.profile.service.UserService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import com.sun.org.apache.bcel.internal.generic.ATHROW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonDao personDao;
    @Autowired UserService userService;
    @Autowired UserDao userDao;
    @Autowired MyUserDetailsService myUserDetailsService;
    @Autowired FileService fileService;
    @Autowired UserFileDao userFileDao;

    private final Logger logger= LoggerFactory.getLogger(PersonServiceImpl.class);
    @Override
    @Transactional
    public Person updatePerson(PersonDto personDto) {
        logger.info("getting profile...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Profile profile =userService.getProfile(userName);
        if(profile.getStatus()== Status.PENDING){

        logger.info("updating person...");
        Person person=profile.getPerson();
        person.setDob(personDto.getDob());
        person.setGender(personDto.getGender());
        person.setResidence(personDto.getResidence());
        person.setNin(personDto.getNin());
        person.setPassport(personDto.getPassport());
        person.setLastName(personDto.getLastName());
        person.setFirstName(personDto.getFirstName());
        logger.info("updating profile...");
        userDao.save(profile);
        return person;
        }
        return profile.getPerson();
    }

    @Override
    public Person getPerson() {
        logger.info("getting profile...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Profile profile =userService.getProfile(userName);
        return profile.getPerson();
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

    @Transactional
    public URL uploadImage(MultipartFile myFile) throws IOException {
        logger.info("getting user....");
        String userName=myUserDetailsService.currentUser();
        logger.info("uploading image ....");
        String fileName =fileService.uploadImage(myFile, userName);
        logger.info("setting image name....");
        Profile profile =userService.getProfile(userName);
        profile.getPerson().setImage(fileName);
        userDao.save(profile);
        logger.info("getting image url....");
        return fileService.signedUrl(fileName);

    }

    public String downloadImage() {

        return null;
    }

    public URL downloadUrl() {
        logger.info("getting user....");
        String userName=myUserDetailsService.currentUser();
        Profile profile =userService.getProfile(userName);
        return fileService.signedUrl(profile.getPerson().getImage());
    }



    public URL signedUrl() {
        logger.info("setting image name....");
        logger.info("getting user....");
        String userName=myUserDetailsService.currentUser();
        Profile profile =userService.getProfile(userName);
        return fileService.signedUrl(profile.getPerson().getImage());
    }

}
