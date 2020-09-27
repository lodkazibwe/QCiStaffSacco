package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {

}
