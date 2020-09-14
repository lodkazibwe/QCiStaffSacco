package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.profile.DAO.ProfileDAO;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    ProfileDAO profileDAO;
    @Override
    public Profile addProfile(Profile profile) {
        return profileDAO.save(profile);
    }

    @Override
    public Profile getProfile(int id) {
        return profileDAO.findById(id).orElse(null);
    }

    @Override
    public List<Profile> getAll() {
        return profileDAO.findAll();
    }

    @Override
    public void deleteProfile(int id) {

    }
}
