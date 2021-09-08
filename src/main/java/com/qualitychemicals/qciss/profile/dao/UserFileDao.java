package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.model.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileDao extends JpaRepository<UserFile, Integer> {

}
