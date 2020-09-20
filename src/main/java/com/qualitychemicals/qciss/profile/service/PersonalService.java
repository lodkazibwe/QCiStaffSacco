package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.DTO.PersonalDTO;
import com.qualitychemicals.qciss.profile.model.Personal;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PersonalService {
     Personal updateInfo(PersonalDTO personalDTO);
     Personal getInfo(int id);
     List<Personal> getAll();
     List<Personal> getByGender(String gender);
     void deletePersonal(int id);
     String updateImage(MultipartFile file, int id) throws IOException;
    //*getByName,search

}
