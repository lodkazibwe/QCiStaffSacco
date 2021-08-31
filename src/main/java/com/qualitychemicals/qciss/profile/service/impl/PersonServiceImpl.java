package com.qualitychemicals.qciss.profile.service.impl;

import com.google.firebase.cloud.StorageClient;
import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.dao.PersonDao;
import com.qualitychemicals.qciss.profile.dao.UserDao;
import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.model.Person;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.service.PersonService;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    PersonDao personDao;
    @Autowired UserService userService;
    @Autowired UserDao userDao;

    private final Logger logger= LoggerFactory.getLogger(PersonServiceImpl.class);
    @Override
    @Transactional
    public Person updatePerson(PersonDto personDto) {
        logger.info("getting profile...");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        Profile profile =userService.getProfile(userName);
        logger.info("updating person...");
        Person person=profile.getPerson();
        person.setDob(personDto.getDob());
        person.setGender(personDto.getGender());
        person.setResidence(personDto.getResidence());
        person.setNin(personDto.getNin());
        logger.info("updating profile...");
        userDao.save(profile);
        return person;
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
        logger.info("getting user....");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("converting image....");
        InputStream file =  new BufferedInputStream(myFile.getInputStream());
        logger.info("getting image name....");
        String fileName=generateFileName(myFile);
        String name="profile/"+userName+"/"+ fileName;
        logger.info("uploading image....");
        StorageClient.getInstance().bucket()
                .create(name, file);
        logger.info("getting user profile....");
        Profile profile=userService.getProfile(userName);
        profile.getPerson().setImage(fileName);
        logger.info("updating profile....");
        userService.updateProfile(profile);
        return name;

    }
    private String generateFileName(MultipartFile multiPart) {

        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename())
                .replace(" ", "_");
    }

    public String downloadImage() {
        /*logger.info("getting user....");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting user profile....");
        Profile profile=userService.getProfile(userName);
        String fileName=profile.getPerson().getImage();
        logger.info("getting image name....");
        String name="profile/"+userName+"/"+ fileName;

        //Storage storage=storageOptions.getService();
        logger.info("getting blob....");
       // Blob blob = storage.get(BlobId.of("qc-sacco.appspot.com", name));
        Blob blob=StorageClient.getInstance().bucket().get(name);
                //.getInstance().bucket().get(name, Storage.BlobGetOption.generationMatch());
        ReadChannel reader = blob.reader();
        InputStream inputStream = Channels.newInputStream(reader);
        byte[] content = null;
        logger.info("success....");
        //Blob blob=StorageClient.
        return blob.getContentType();*/
        return null;
    }

    public String downloadUrl() {
        logger.info("getting user....");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting user profile....");
        Profile profile=userService.getProfile(userName);
        String fileName=profile.getPerson().getImage();
        logger.info("getting image name....");
        String name="profile/"+userName+"/"+ fileName;
        //return StorageClient.getInstance().bucket().get(name).signUrl(14, TimeUnit.DAYS);
        //Blob.signUrl().
        return StorageClient.getInstance().bucket().get(name).getSelfLink();
    }

    public URL signedUrl() {
        logger.info("getting user....");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        logger.info("getting user profile....");
        Profile profile=userService.getProfile(userName);
        String fileName=profile.getPerson().getImage();
        logger.info("getting image name....");
        String name="profile/"+userName+"/"+ fileName;
        return StorageClient.getInstance().bucket().get(name).signUrl(14, TimeUnit.DAYS);

    }

}
