package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.model.Profile;

import java.util.List;

public interface ProfileService {
    Profile addProfile(Profile profile);
    Profile getProfile(int id);
    List<Profile> getAll();
    void deleteProfile(int id);

}
