package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.DTO.ProfileDTO;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.Profile;
import com.qualitychemicals.qciss.profile.model.Summary;
import com.qualitychemicals.qciss.profile.model.Work;

import java.util.List;

public interface ProfileService {
    Profile addProfile(ProfileDTO profileDTO);
    Profile updateProfile(Profile profile);
    Profile getProfile(int id);
    List<Account> getAccounts(int id);
    Summary getSummary(int id);
    Work getWorkInfo(int id);
    List<Profile> getAll();
    void deleteProfile(int id);

}
