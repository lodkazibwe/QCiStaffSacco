package com.qualitychemicals.qciss.profile.DAO;

import com.qualitychemicals.qciss.profile.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO  extends JpaRepository<Role, Integer> {

}
