package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.model.Personal;

import java.util.List;

public interface PersonalService {
    Personal updatePersonal(Personal personal);
    Personal getPersonal(int id);
    Personal getByProfile(int id);
    List<Personal> getAll();
    List<Personal> getByGender(String gender);
    void deletePersonal(int id);
    //*getByName,search

}
