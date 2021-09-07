package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.dto.*;
import com.qualitychemicals.qciss.profile.model.*;

import java.util.List;

public interface UserService {
    Profile addProfile(UserDto userDTO, String role, Status status);
    Profile signUp(PersonDto personDto);
    Profile updateProfile(Profile profile);
    String updatePass(String userName, String pass);
    String createPass(String pass);
    String requestPin(String contact);
    Profile getProfile(int id);
    Profile getProfile(String userName);
    NextOfKin addNextOfKin(NextOfKinDto nextOfKinDto);

    String verifyAccount(String accountNumber, String userName);
    Account getSummary(int id);
    Work getWorkInfo(int id);
    Work getWorkInfo(String userName);
    List<Profile> getAll();
    boolean isUserOpen(String userName);
    boolean isUserClosed(String userName);
    void deleteProfile(int id);
    Account getAccount(String userName);
    Profile verifyUser(int userId);
    Profile closeUser(int userId);
    List<EmployeeDto> getEmployees(String company);
    List<DeductionScheduleDTO> deductionSchedule(String company);

    List<Profile> getAllOpen();

    List<Profile> getAllPending();

    List<Profile> getAllClosed();

    void addRole(String userName,String Role);

    List<PersonDto> getAdmins();

    //Profile addRoot(PersonDto personDto);
}
