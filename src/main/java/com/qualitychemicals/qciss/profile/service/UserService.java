package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.dto.UserDto;
import com.qualitychemicals.qciss.profile.model.Status;
import com.qualitychemicals.qciss.profile.model.User;
import com.qualitychemicals.qciss.profile.model.Account;
import com.qualitychemicals.qciss.profile.model.Work;

import java.util.List;

public interface UserService {
    User addProfile(UserDto userDTO, String role, Status status);
    User signUp(PersonDto personDto);
    User updateProfile(User user);
    String updatePass(String userName, String pass);
    String createPass(String pass);
    String requestPin(String contact);
    User getProfile(int id);
    User getProfile(String userName);
    String verifyAccount(String accountNumber, String userName);
    Account getSummary(int id);
    Work getWorkInfo(int id);
    Work getWorkInfo(String userName);
    List<User> getAll();
    boolean isUserOpen(String userName);
    boolean isUserClosed(String userName);
    void deleteProfile(int id);

}
