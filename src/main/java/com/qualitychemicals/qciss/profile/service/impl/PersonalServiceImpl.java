package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.DAO.PersonalDAO;
import com.qualitychemicals.qciss.profile.model.Personal;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.rest.v1.ProfileRest;
import com.qualitychemicals.qciss.profile.service.PersonalService;
import com.qualitychemicals.qciss.profile.service.ProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PersonalServiceImpl implements PersonalService {
    @Autowired
    PersonalDAO personalDAO;

    private final Logger logger= LoggerFactory.getLogger(ProfileRest.class);
    @Override
    public Personal updateInfo(Personal personal) {
        return null;
    }

    @Override
    public Personal getInfo(int id) {
        return personalDAO.findById(id).orElseThrow(()->new ResourceNotFoundException("No such info ID: "+id));
    }

    @Override
    public List<Personal> getAll() {
        return personalDAO.findAll();
    }

    @Override
    public List<Personal> getByGender(String gender) {
        return personalDAO.findByGender(gender);
    }

    @Override
    public void deletePersonal(int id) {

    }

    @Override
    @Transactional
    public String updateImage(MultipartFile file, int id) throws IOException {
        logger.info("personalID= "+id);
        logger.info("uploadeing image...");
        File newFile=new File("C:\\Users\\joeko\\Desktop\\file\\"+file.getOriginalFilename());
        file.transferTo(newFile);
        logger.info("saving...");
        Personal personal=personalDAO.findById(id).
                orElseThrow(()->new ResourceNotFoundException("No such account with ID: "+id));
        personal.setImage(newFile.getAbsolutePath());
        personalDAO.save(personal);
        logger.info("saved successfully...");
        return newFile.getAbsolutePath();

    }
}
