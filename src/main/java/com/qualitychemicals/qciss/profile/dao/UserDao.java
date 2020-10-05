package com.qualitychemicals.qciss.profile.dao;

import com.qualitychemicals.qciss.profile.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    boolean existsByUserName(String userName);

    User findByUserName(String userName);

}
