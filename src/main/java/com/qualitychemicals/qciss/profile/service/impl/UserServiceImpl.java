package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.DAO.UserDAO;
import com.qualitychemicals.qciss.profile.model.User;
import com.qualitychemicals.qciss.profile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;
    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public boolean usernameExists(String userName) {
        return userDAO.existsByUserName(userName);
    }

    @Override
    public User getUser(int id) {
        return userDAO.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No such user: "+id));
    }

    @Override
    public User getByProfile(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return userDAO.findAll();
    }

    @Override
    public List<User> getByRole(String Role) {
        return null;
    }

    @Override
    public List<User> getByStatus(String status) {
        return userDAO.findByStatus(status);
    }

    @Override
    public User getByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }

    @Override
    public void deleteUser(int id) {

    }
}
