package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.model.Role;

import java.util.List;

public interface RoleService {
    Role addRole(Role role);
    Role updateRole(Role role);
    Role getRole(int id);
    List<Role> getByUserId(int id);
    List<Role> getByProfile(int id);
    void deleteRole(int id);

}
