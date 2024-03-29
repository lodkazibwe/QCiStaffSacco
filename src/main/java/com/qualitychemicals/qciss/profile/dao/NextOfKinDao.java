package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.model.NextOfKin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NextOfKinDao extends JpaRepository<NextOfKin, Integer> {

}
