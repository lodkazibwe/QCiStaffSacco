package com.qualitychemicals.qciss.profile.service;

import com.qualitychemicals.qciss.profile.model.Role;


public interface RoleService {
    Role addRole(Role role);
    Role getRole(int id);
    //Role updateRole(Role role, int id);
    void deleteRole(int id);

}
