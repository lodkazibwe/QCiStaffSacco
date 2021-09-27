package com.qualitychemicals.qciss.firebase.file;

import com.google.firebase.cloud.StorageClient;
import com.qualitychemicals.qciss.security.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class FileService {
    @Autowired
    MyUserDetailsService myUserDetailsService;

    private final Logger logger = LoggerFactory.getLogger(FileService.class);

    public String uploadImage(MultipartFile myFile, String userName) throws IOException {

        logger.info("converting image....");
        InputStream file =  new BufferedInputStream(myFile.getInputStream());
        logger.info("getting image name....");
        String fileName=generateFileName(myFile);
        String name="qcProfile/"+userName+"/"+ fileName;
        logger.info("uploading image....");
        StorageClient.getInstance().bucket()
                .create(name, file);
        logger.info("updating profile....");
        //return bucket(name);
        return name;

   }

    public String uploadFile(MultipartFile myFile, String userName) throws IOException {

        logger.info("converting image....");
        InputStream file =  new BufferedInputStream(myFile.getInputStream());
        logger.info("getting image name....");
        String fileName=generateFileName(myFile);
        String name="qcFile/"+userName+"/"+ fileName;
        logger.info("uploading image....");
        StorageClient.getInstance().bucket()
                .create(name, file);
        logger.info("updating profile....");
        return name;

    }

    private String generateFileName(MultipartFile multiPart) {

        return new Date().getTime() + "-" + Objects.requireNonNull(multiPart.getOriginalFilename())
                .replace(" ", "_");
    }

    public URL signedUrl(String imageName) {
       logger.info("getting image....");
        StorageClient.getInstance().bucket().get(imageName).getBucket();
        return StorageClient.getInstance().bucket().get(imageName).signUrl(14, TimeUnit.DAYS);

    }

    public String bucket(String imageName) {
        logger.info("getting image....");
        return StorageClient.getInstance().bucket().get(imageName).getMediaLink();

    }

}
