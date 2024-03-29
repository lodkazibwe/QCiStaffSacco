package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.model.FileCat;
import com.qualitychemicals.qciss.profile.model.UserFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface UserFileService {

    URL fileUrl(int id);
    UserFile uploadFile(MultipartFile myFile, String cat)throws IOException;
    List<UserFile> userFiles(String userName);
    FileCat addFileCat(FileCat fileCat);
    List<FileCat> getCategories();

}
