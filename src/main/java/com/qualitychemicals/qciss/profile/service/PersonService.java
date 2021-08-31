package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.model.Person;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PersonService {
     Person updatePerson(PersonDto personDto);
     Person getPerson(int id);
     List<Person> getAll();
     List<Person> getByGender(String gender);
     void deletePerson(int id);
     int userExists(String email, String mobile);
     String updateImage(MultipartFile file, int id) throws IOException;
     String uploadImage(MultipartFile myFile) throws IOException;
     String downloadImage();
     String downloadUrl();

}
