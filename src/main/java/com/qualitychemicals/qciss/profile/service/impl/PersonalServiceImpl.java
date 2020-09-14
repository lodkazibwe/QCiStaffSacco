package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.profile.DAO.PersonalDAO;
import com.qualitychemicals.qciss.profile.model.Personal;
import com.qualitychemicals.qciss.profile.service.PersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalServiceImpl implements PersonalService {
    @Autowired
    PersonalDAO personalDAO;
    @Override
    public Personal updatePersonal(Personal personal) {
        return null;
    }

    @Override
    public Personal getPersonal(int id) {
        return personalDAO.findById(id).orElse(null);
    }

    @Override
    public Personal getByProfile(int id) {
        return null;
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
}
