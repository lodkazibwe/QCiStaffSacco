package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkDao extends JpaRepository<Work, Integer> {

    List<Work> findByCompany(String company);

}
