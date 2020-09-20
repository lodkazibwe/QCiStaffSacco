package com.qualitychemicals.qciss.profile.service.impl;

import com.qualitychemicals.qciss.exceptions.ResourceNotFoundException;
import com.qualitychemicals.qciss.profile.DAO.RoleDAO;
import com.qualitychemicals.qciss.profile.model.Role;
import com.qualitychemicals.qciss.profile.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleDAO roleDAO;
    @Override
    public Role addRole(Role role) {
        return roleDAO.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        return null;
    }

    @Override
    public Role getRole(int id) {
        return roleDAO.findById(id).orElseThrow(()->new ResourceNotFoundException("Role Not Found"+id));
    }

    @Override
    public List<Role> getByUserId(int id) {
        return null;
    }

    @Override
    public List<Role> getByProfile(int id) {
        return null;
    }

    @Override
    public void deleteRole(int id) {

    }
}
