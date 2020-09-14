package com.qualitychemicals.qciss.profile.DAO;

import com.qualitychemicals.qciss.profile.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileDAO extends JpaRepository<Profile, Integer> {

}
