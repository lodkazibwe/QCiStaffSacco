package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.firebase.file.FileService;
import com.qualitychemicals.qciss.profile.dao.FileCatDao;
import com.qualitychemicals.qciss.profile.dao.UserFileDao;
import com.qualitychemicals.qciss.profile.model.FileCat;
import com.qualitychemicals.qciss.profile.model.UserFile;
import com.qualitychemicals.qciss.profile.service.UserFileService;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserFileServiceImpl implements UserFileService {
    @Autowired
    FileService fileService;
    @Autowired
    MyUserDetailsService myUserDetailsService;
    @Autowired
    UserFileDao userFileDao;
    @Autowired FileCatDao fileCatDao;
    private final Logger logger= LoggerFactory.getLogger(UserFileServiceImpl.class);

    @Transactional
    @Override
    public UserFile uploadFile(MultipartFile myFile, String cat) throws IOException {
        logger.info("getting user....");
        String userName=myUserDetailsService.currentUser();
        logger.info("uploading file ....");
        String file =fileService.uploadFile(myFile, userName);
        logger.info("setting file name....");
        UserFile userFile =new UserFile();
        userFile.setUserName(userName);
        userFile.setFilePath(file);
        userFile.setCategory(fileCatDao.findByCatName(cat));
        userFile.setFileName(generateFileName(myFile));//
        logger.info("saving file ....");
        return userFileDao.save(userFile);

    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename())
                .replace(" ", "_");
    }

    @Override
    public URL fileUrl(int id) {
        UserFile userFile =userFileDao.findById(id).orElseThrow(()->new ResourceNotFoundException("file Not Found: "+id));
        return fileService.signedUrl(userFile.getFilePath());
    }

    @Override
    public List<UserFile> userFiles(String userName) {
        return userFileDao.findByUserName(userName);
    }

    @Override
    public FileCat addFileCat(FileCat fileCat) {
        FileCat newCat =new FileCat();
        newCat.setCatName(fileCat.getCatName());
        newCat.setDescription(fileCat.getDescription());
        return fileCatDao.save(newCat);
    }

    @Override
    public List<FileCat> getCategories() {
        return fileCatDao.findAll();
    }

}
