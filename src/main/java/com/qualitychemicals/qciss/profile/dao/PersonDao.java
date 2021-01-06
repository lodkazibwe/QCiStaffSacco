package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.dto.PersonDto;
import com.qualitychemicals.qciss.profile.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonDao extends JpaRepository<Person, Integer> {

    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);
    List<Person> findByGender(String gender);

}
