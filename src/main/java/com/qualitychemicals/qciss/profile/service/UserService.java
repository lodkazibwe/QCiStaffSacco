package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.model.User;

import java.util.List;

public interface UserService {
    User updateUser(User user);
    User getUser(int id);
    User getByProfile(int id);
    List<User> getAll();
    List<User> getByRole(String Role);
    List<User> getByStatus(String status);
    User getByUserName(String userName);
    boolean usernameExists(String userName);
    void deleteUser(int id);

}
