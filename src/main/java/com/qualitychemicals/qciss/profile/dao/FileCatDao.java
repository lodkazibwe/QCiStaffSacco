package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.model.FileCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileCatDao extends JpaRepository<FileCat, Integer> {
    FileCat findByCatName(String name);
    //List<Work> findByCompanyName(String company);
}
