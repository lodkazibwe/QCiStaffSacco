package com.qualitychemicals.qciss.profile.DAO;

import com.qualitychemicals.qciss.profile.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    List<User> findByStatus(String status);
    User findByUserName(String userName);
    boolean existsByUserName(String userName);
}
