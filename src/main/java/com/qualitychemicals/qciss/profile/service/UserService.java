package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.User;
import com.qualitychemicals.qciss.profile.model.Summary;
import com.qualitychemicals.qciss.profile.model.Work;

import java.util.List;

public interface UserService {
    User addProfile(UserDto userDTO);
    User updateProfile(User user);
    User getProfile(int id);
    List<Account> getAccounts(int id);
    Summary getSummary(int id);
    Work getWorkInfo(int id);
    List<User> getAll();
    void deleteProfile(int id);

}
